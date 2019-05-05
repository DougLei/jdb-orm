package com.douglei.sessions.session.sql;

import java.util.List;
import java.util.Map;

import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.database.sql.ConnectionWrapper;
import com.douglei.sessions.session.Session;
import com.douglei.sessions.session.query.Query;
import com.douglei.sessions.sqlsession.SqlSessionImpl;

/**
 * 
 * @author DougLei
 */
public class SQLSession extends SqlSessionImpl implements Session {
//	private static final Logger logger = LoggerFactory.getLogger(SQLSession.class);
	
	public SQLSession(ConnectionWrapper connection, EnvironmentProperty environmentProperty, MappingWrapper mappingWrapper) {
		super(connection, environmentProperty, mappingWrapper);
	}

	@Override
	public void save(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(String code, Map<String, Object> propertyMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String code, Map<String, Object> propertyMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String code, Map<String, Object> propertyMap) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Query createQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> query(Query query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T uniqueQuery(Query query) {
		// TODO Auto-generated method stub
		return null;
	}
}
