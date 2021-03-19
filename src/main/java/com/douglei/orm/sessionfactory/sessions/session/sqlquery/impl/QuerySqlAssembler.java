package com.douglei.orm.sessionfactory.sessions.session.sqlquery.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.mapping.impl.sqlquery.metadata.SqlQueryMetadata;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.ExecutableSql;

/**
 * 
 * @author DougLei
 */
public class QuerySqlAssembler {
	private static final Logger logger = LoggerFactory.getLogger(QuerySqlAssembler.class);
	private SqlQueryMetadata metadata;
	private ExecutableSql executableSql;
	private List<AbstractParameter> parameters;
	
	QuerySqlAssembler(SqlQueryMetadata metadata, ExecutableSql executableSql, List<AbstractParameter> parameters) {
		this.metadata = metadata;
		this.executableSql = executableSql;
		this.parameters = parameters;
	}

	/**
	 * 装配
	 * @return
	 */
	public ExecutableQuerySql assembling() {
		ExecutableQuerySql sql = new ExecutableQuerySql(executableSql);
		
		if(parameters != null) {
			for(int i=0;i<parameters.size();i++) {
				parameters.get(i).assembleSQL(sql, metadata);
				if(i < parameters.size()-1)
					sql.appendConditionSQLNext(parameters.get(i).next);
			}
		}
		
		// 验证是否没有传入必要的参数
		metadata.getParameterMap().values().forEach(parameter -> {
			if(parameter.isRequired() && !sql.existsInConditionSQL(parameter.getName()))
				throw new QuerySqlAssembleException("装配code为["+metadata.getCode()+"]的query-sql时, 未传入必要的参数["+parameter.getName()+"]");
		});
		
		logger.debug("ExecutableQuerySql={}", sql);
		return sql;
	}
}