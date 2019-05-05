package com.douglei.sessions.session.sql.impl;

import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.database.sql.ConnectionWrapper;
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
}
