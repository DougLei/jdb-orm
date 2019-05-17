package com.douglei.sessions.session.sql.impl.persistent.execution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.metadata.sql.SqlContentMetadata;
import com.douglei.database.metadata.sql.SqlMetadata;
import com.douglei.database.metadata.sql.SqlParameterMetadata;
import com.douglei.database.sql.statement.LocalRunDialect;
import com.douglei.database.sql.statement.entity.InputSqlParameter;
import com.douglei.sessions.session.persistent.execution.ExecutionHolder;

/**
 * 
 * @author DougLei
 */
public class SqlExecutionHolder implements ExecutionHolder{
	private static final Logger logger = LoggerFactory.getLogger(SqlExecutionHolder.class);

	public SqlExecutionHolder(SqlMetadata sqlMetadata, Map<String, Object> sqlParameterMap) {
		String dialectTypeCode = LocalRunDialect.getDialect().getType().getCode();
		List<SqlContentMetadata> contents = sqlMetadata.getContents(dialectTypeCode);
		if(contents == null || contents.size() == 0) {
			throw new NullPointerException("sql code="+sqlMetadata.getCode()+", dialect="+dialectTypeCode+", 不存在可以执行的sql语句");
		}
		
		executeSqlCount = contents.size();
		executeSqls = new ArrayList<String>(executeSqlCount);
		parametersList = new ArrayList<List<Object>>(executeSqlCount);
		
		List<SqlParameterMetadata> sqlParameters;
		for (SqlContentMetadata content : contents) {
			sqlParameters = content.getSqlParameterOrders();
			if(sqlParameters == null || sqlParameters.size() == 0) {
				addExecuteSql(content.getContent());
				addParameters(null);
			}else {
				setExecuteSql(content.getContent(), content.getPlaceholderCount(), sqlParameters, sqlParameterMap);
			}
		}
	}
	
	// 设置 executeSql 和 parameters, 将结果add到executeSqls和parametersList集合中
	private void setExecuteSql(String content, int placeholderCount, List<SqlParameterMetadata> sqlParameters, Map<String, Object> sqlParameterMap) {
		List<Object> parameters = new ArrayList<Object>(placeholderCount);
		
		Object value = null;
		for (SqlParameterMetadata parameter : sqlParameters) {
			value = sqlParameterMap.get(parameter.getName());
			
			if(parameter.isUsePlaceholder()) {
				parameters.add(new InputSqlParameter(value, parameter.getDataTypeHandler()));
			}else {
				content = content.replaceFirst("\\$\\{"+parameter.getName()+"\\}\\$", parameter.getPlaceholderPrefix()+value+parameter.getPlaceholderSuffix());
			}
		}

		addExecuteSql(content);
		addParameters(parameters);
	}
	
	private void addExecuteSql(String sqlContent) {
		logger.debug("要执行的sql content= {}", sqlContent);
		executeSqls.add(sqlContent);
	}
	private void addParameters(List<Object> parameters) {
		if(logger.isDebugEnabled()) {
			logger.debug("对应的parameters= {}", parameters==null?"无参数": parameters.toString());
		}
		parametersList.add(parameters);
	}

	private int executeSqlCount;
	private int executeSqlIndex; // 从0开始
	private List<String> executeSqls;
	private List<List<Object>> parametersList;
	
	@Override
	public int executeSqlCount() {
		return executeSqlCount;
	}

	@Override
	public boolean next() {
		executeSqlIndex++;
		return executeSqlIndex < executeSqlCount;
	}

	@Override
	public String getCurrentSql() {
		if(logger.isDebugEnabled()) {
			logger.debug("获取第{}个 executeSql", executeSqlIndex+1);
		}
		return executeSqls.get(executeSqlIndex);
	}

	@Override
	public List<Object> getCurrentParameters() {
		if(logger.isDebugEnabled()) {
			logger.debug("获取第{}个 parameters", executeSqlIndex+1);
		}
		return parametersList.get(executeSqlIndex);
	}

}
