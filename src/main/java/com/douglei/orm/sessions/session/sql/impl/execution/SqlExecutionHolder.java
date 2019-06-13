package com.douglei.orm.sessions.session.sql.impl.execution;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.context.RunMappingConfigurationContext;
import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.metadata.sql.SqlContentMetadata;
import com.douglei.orm.core.metadata.sql.SqlMetadata;
import com.douglei.orm.sessions.session.execution.ExecutionHolder;

/**
 * 
 * @author DougLei
 */
public class SqlExecutionHolder implements ExecutionHolder{
	private static final Logger logger = LoggerFactory.getLogger(SqlExecutionHolder.class);

	public SqlExecutionHolder(SqlMetadata sqlMetadata, Object sqlParameter) {
		DialectType dialect = DBRunEnvironmentContext.getDialect().getType();
		List<SqlContentMetadata> contents = sqlMetadata.getContents(dialect);
		if(contents == null || contents.size() == 0) {
			throw new NullPointerException(RunMappingConfigurationContext.getCurrentExecuteMappingDescription()+", 不存在可以执行的sql语句");
		}
		
		executeSqlCount = contents.size();
		executeSqls = new ArrayList<ExecuteSql>(executeSqlCount);
		
		for (SqlContentMetadata content : contents) {
			executeSqls.add(new ExecuteSql(content, sqlParameter));
		}
	}
	
	private int executeSqlCount;
	private int executeSqlIndex; // 从0开始
	private List<ExecuteSql> executeSqls;
	
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
		return executeSqls.get(executeSqlIndex).getContent();
	}

	@Override
	public List<Object> getCurrentParameters() {
		if(logger.isDebugEnabled()) {
			logger.debug("获取第{}个 parameters", executeSqlIndex+1);
		}
		return executeSqls.get(executeSqlIndex).getParameters();
	}
}
