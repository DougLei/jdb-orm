package com.douglei.orm.factory.sessions.session.sql.impl.execution;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.context.ExecMappingDescriptionContext;
import com.douglei.orm.core.metadata.sql.ContentMetadata;
import com.douglei.orm.core.metadata.sql.SqlMetadata;
import com.douglei.orm.factory.sessions.session.execution.ExecutionHolder;

/**
 * 
 * @author DougLei
 */
public class SqlExecutionHolder implements ExecutionHolder{
	private static final Logger logger = LoggerFactory.getLogger(SqlExecutionHolder.class);

	public SqlExecutionHolder(SqlMetadata sqlMetadata, String name, Object sqlParameter) {
		List<ContentMetadata> contents = sqlMetadata.getContents(name);
		if(contents == null || contents.size() == 0) {
			throw new NullPointerException(ExecMappingDescriptionContext.getExecMappingDescription()+", 不存在可以执行的sql语句");
		}
		
		executeSqlCount = (byte) contents.size();
		executeSqls = new ArrayList<ExecuteSql>(executeSqlCount);
		
		for (ContentMetadata content : contents) {
			executeSqls.add(new ExecuteSql(content, sqlParameter));
		}
	}
	
	private byte executeSqlCount;
	private byte executeSqlIndex; // 从0开始
	private List<ExecuteSql> executeSqls;
	
	@Override
	public short executeSqlCount() {
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
