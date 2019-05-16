package com.douglei.sessions.session.sql.impl;

import java.util.List;
import java.util.Map;

import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.database.sql.ConnectionWrapper;
import com.douglei.database.sql.pagequery.PageResult;
import com.douglei.sessions.session.sql.SQLSession;
import com.douglei.sessions.sqlsession.impl.SqlSessionImpl;

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
		Map<String, Object> parameters = null;
		return query(code, parameters);
	}
	
	@Override
	public List<Map<String, Object>> query(String code, Map<String, Object> parameters) {
		return null;
	}
	
	@Override
	public Map<String, Object> uniqueQuery(String code) {
		Map<String, Object> parameters = null;
		return uniqueQuery(code, parameters);
	}
	
	@Override
	public Map<String, Object> uniqueQuery(String code, Map<String, Object> parameters) {
		return null;
	}
	
	@Override
	public List<Object[]> query_(String code) {
		Map<String, Object> parameters = null;
		return query_(code, parameters);
	}

	@Override
	public List<Object[]> query_(String code, Map<String, Object> parameters) {
		return null;
	}

	@Override
	public Object[] uniqueQuery_(String code) {
		Map<String, Object> parameters = null;
		return uniqueQuery_(code, parameters);
	}

	@Override
	public Object[] uniqueQuery_(String code, Map<String, Object> parameters) {
		return null;
	}
	
	@Override
	public int executeUpdate(String code) {
		Map<String, Object> parameters = null;
		return executeUpdate(code, parameters);
	}
	
	@Override
	public int executeUpdate(String code, Map<String, Object> parameters) {
		return -1;
	}
	
	@Override
	public PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String code) {
		Map<String, Object> parameters = null;
		return pageQuery(pageNum, pageSize, code, parameters);
	}

	@Override
	public PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String code, Map<String, Object> parameters) {
		return null;
	}
}
