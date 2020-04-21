package com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.core.metadata.sql.ContentMetadata;
import com.douglei.orm.core.metadata.sql.SqlMetadata;
import com.douglei.orm.sessionfactory.sessions.session.execute.ExecuteHandler;

/**
 * 
 * @author DougLei
 */
public class SqlExecuteHandler implements ExecuteHandler{
	private static final Logger logger = LoggerFactory.getLogger(SqlExecuteHandler.class);

	private short executeSqlCount; // 要执行的sql的数量
	private short executeSqlIndex; // 执行的sql的下标, 从0开始
	private List<ExecuteSql> executeSqls;
	
	public SqlExecuteHandler(SqlMetadata sqlMetadata, String name, Object sqlParameter) {
		List<ContentMetadata> contents = sqlMetadata.getContents(name);
		if(contents.size() == 0) {
			throw new NullPointerException("不存在任何可以执行的sql语句");
		}
		
		executeSqlCount = (short)contents.size();
		executeSqls = new ArrayList<ExecuteSql>(executeSqlCount);
		
		for (ContentMetadata content : contents) {
			executeSqls.add(new ExecuteSql(content, sqlParameter));
		}
	}
	
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
