package com.douglei.session.impl;

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
import com.douglei.session.AbstractSession;
import com.douglei.session.SqlSession;
import com.douglei.utils.CryptographyUtil;
import com.douglei.utils.StringUtil;

/**
 * 执行sql语句的session实现类
 * @author DougLei
 */
public class SqlSessionImpl extends AbstractSession implements SqlSession{
	private static final Logger logger = LoggerFactory.getLogger(SqlSessionImpl.class);
	private Map<String, StatementHandler> statementHandlerCache;
	
	
	public SqlSessionImpl(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		super(connection, environmentProperty, mappingWrapper);
	}

	/**
	 * 获取StatementHandler
	 * @param sql
	 * @param noParameter
	 * @return
	 */
	private StatementHandler getStatementHandler(String sql, boolean noParameter){
		StatementHandler statementHandler = null;
		if(enableSessionCache) {
			String code = CryptographyUtil.encodeMD5(sql);
			
			if(statementHandlerCache == null) {
				statementHandlerCache = new HashMap<String, StatementHandler>(16);
			}else {
				statementHandler = statementHandlerCache.get(code);
			}
			
			if(statementHandler == null) {
				statementHandler = connection.createStatementHandler(sql, noParameter);
				statementHandlerCache.put(code, statementHandler);
			}
		}else {
			statementHandler = connection.createStatementHandler(sql, noParameter);
		}
		return statementHandler;
	}
	
	@Override
	public List<Map<String, Object>> query(String sql) {
		return query(sql, null);
	}

	@Override
	public List<Map<String, Object>> query(String sql, List<Object> parameters) {
		if(StringUtil.isEmpty(sql)) {
			throw new NullPointerException(getClass() + "query(String, List<Object>)查询传入的sql语句不能为空");
		}
		if(logger.isDebugEnabled()) {
			logger.debug("要执行query的sql语句为: {}", sql);
			if(parameters==null || parameters.size() == 0) {
				logger.debug("没有传入的参数");
			}else {
				logger.debug("传入的参数为: {}", parameters.toString());
			}
		}
		StatementHandler statementHandler = null;
		try {
			boolean noParameter = (parameters == null || parameters.size() == 0);
			statementHandler = getStatementHandler(sql, noParameter);
			return statementHandler.getQueryResultList(parameters);
		} finally {
			if(!enableSessionCache) {
				statementHandler.close();
			}
		}
	}
	
	@Override
	protected void flush() {
	}

	@Override
	public void close() {
		flush();
		super.close();
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
