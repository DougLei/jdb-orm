package com.douglei.sessions.sqlsession;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.database.sql.ConnectionWrapper;
import com.douglei.database.sql.statement.StatementHandler;
import com.douglei.utils.CryptographyUtil;

/**
 * 执行sql语句的session实现类
 * @author DougLei
 */
public class SqlSessionImpl implements SqlSession{
	private static final Logger logger = LoggerFactory.getLogger(SqlSessionImpl.class);
	
	protected ConnectionWrapper connection;
	protected EnvironmentProperty environmentProperty;
	protected MappingWrapper mappingWrapper;
	
	protected boolean enableSessionCache;// 是否启用session缓存
	private Map<String, StatementHandler> statementHandlerCache;
	
	public SqlSessionImpl(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		this.connection = connection;
		this.environmentProperty = environmentProperty;
		this.mappingWrapper = mappingWrapper;
		this.enableSessionCache = environmentProperty.getEnableSessionCache();
	}

	/**
	 * 获取StatementHandler
	 * @param sql
	 * @param parameters
	 * @return
	 */
	private StatementHandler getStatementHandler(String sql, List<? extends Object> parameters){
		StatementHandler statementHandler = null;
		if(enableSessionCache) {
			logger.debug("缓存开启, 从缓存中获取StatementHandler实例");
			String code = CryptographyUtil.encodeMD5(sql);
			
			if(statementHandlerCache == null) {
				statementHandlerCache = new HashMap<String, StatementHandler>(16);
			}else {
				statementHandler = statementHandlerCache.get(code);
			}
			
			if(statementHandler == null) {
				logger.debug("缓存中不存在相关的StatementHandler实例, 创建实例并放到缓存中");
				statementHandler = connection.createStatementHandler(sql, parameters);
				statementHandlerCache.put(code, statementHandler);
			}
		}else {
			logger.debug("没有开启缓存, 创建StatementHandler实例");
			statementHandler = connection.createStatementHandler(sql, parameters);
		}
		return statementHandler;
	}
	
	@Override
	public List<Map<String, Object>> query(String sql) {
		return query(sql, null);
	}
	
	@Override
	public List<Map<String, Object>> query(String sql, List<Object> parameters) {
		StatementHandler statementHandler = null;
		try {
			statementHandler = getStatementHandler(sql, parameters);
			return statementHandler.getQueryResultList(parameters);
		} finally {
			if(!enableSessionCache) {
				statementHandler.close();
			}
		}
	}
	
	@Override
	public Map<String, Object> uniqueQuery(String sql) {
		return uniqueQuery(sql, null);
	}
	
	@Override
	public Map<String, Object> uniqueQuery(String sql, List<Object> parameters) {
		StatementHandler statementHandler = null;
		try {
			statementHandler = getStatementHandler(sql, parameters);
			return statementHandler.getQueryUniqueResult(parameters);
		} finally {
			if(!enableSessionCache) {
				statementHandler.close();
			}
		}
	}
	
	@Override
	public List<Object[]> query_(String sql) {
		return query_(sql, null);
	}

	@Override
	public List<Object[]> query_(String sql, List<Object> parameters) {
		StatementHandler statementHandler = null;
		try {
			statementHandler = getStatementHandler(sql, parameters);
			return statementHandler.getQueryResultList_(parameters);
		} finally {
			if(!enableSessionCache) {
				statementHandler.close();
			}
		}
	}

	@Override
	public Object[] uniqueQuery_(String sql) {
		return uniqueQuery_(sql, null);
	}

	@Override
	public Object[] uniqueQuery_(String sql, List<Object> parameters) {
		StatementHandler statementHandler = null;
		try {
			statementHandler = getStatementHandler(sql, parameters);
			return statementHandler.getQueryUniqueResult_(parameters);
		} finally {
			if(!enableSessionCache) {
				statementHandler.close();
			}
		}
	}
	
	@Override
	public int executeUpdate(String sql) {
		return executeUpdate(sql, null);
	}
	
	@Override
	public int executeUpdate(String sql, List<? extends Object> parameters) {
		StatementHandler statementHandler = null;
		try {
			statementHandler = getStatementHandler(sql, parameters);
			return statementHandler.executeUpdate(parameters);
		} finally {
			if(!enableSessionCache) {
				statementHandler.close();
			}
		}
	}
	
	@Override
	public void commit() {
		connection.commit();
	}
	
	@Override
	public void rollback() {
		connection.rollback();
	}
	
	@Override
	public void close() {
		if(!connection.isFinishTransaction()) {
			logger.info("当前[{}]的事物没有处理结束: commit 或 rollback, 程序默认进行 commit操作", getClass());
			connection.commit();
		}
		if(enableSessionCache) {
			if(statementHandlerCache != null && statementHandlerCache.size() > 0) {
				Collection<StatementHandler> statementHandlers = statementHandlerCache.values();
				for (StatementHandler statementHandler : statementHandlers) {
					statementHandler.close();
				}
				statementHandlerCache.clear();
			}
		}
	}
}
