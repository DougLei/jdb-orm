package com.douglei.sessions.session.sql.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.database.sql.ConnectionWrapper;
import com.douglei.database.sql.pagequery.PageResult;
import com.douglei.database.sql.pagequery.PageSqlStatement;
import com.douglei.database.sql.statement.LocalDialect;
import com.douglei.database.sql.statement.StatementHandler;
import com.douglei.sessions.session.sql.SQLSession;
import com.douglei.sessions.sqlsession.SqlSessionImpl;

/**
 * 
 * @author DougLei
 */
public class SQLSessionImpl extends SqlSessionImpl implements SQLSession {
//	private static final Logger logger = LoggerFactory.getLogger(SQLSession.class);
	
	public SQLSessionImpl(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		super(connection, environmentProperty, mappingWrapper);
	}
	
	@Override
	public List<Map<String, Object>> query(String code) {
		return query(code, null);
	}
	
	@Override
	public List<Map<String, Object>> query(String code, Map<String, Object> parameters) {
	}
	
	@Override
	public Map<String, Object> uniqueQuery(String code) {
		return uniqueQuery(code, null);
	}
	
	@Override
	public Map<String, Object> uniqueQuery(String code, Map<String, Object> parameters) {
	}
	
	@Override
	public List<Object[]> query_(String code) {
		return query_(code, null);
	}

	@Override
	public List<Object[]> query_(String code, Map<String, Object> parameters) {
	}

	@Override
	public Object[] uniqueQuery_(String code) {
		return uniqueQuery_(code, null);
	}

	@Override
	public Object[] uniqueQuery_(String code, Map<String, Object> parameters) {
	}
	
	@Override
	public int executeUpdate(String code) {
		return executeUpdate(code, null);
	}
	
	@Override
	public int executeUpdate(String code, Map<String, Object> parameters) {
	}
	
	@Override
	public PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String code) {
		return pageQuery(pageNum, pageSize, code, null);
	}

	@Override
	public PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String code, Map<String, Object> parameters) {
		return null;
	}
}
