package com.douglei.orm.sessionfactory.sessions.session.sql.impl;

import com.douglei.orm.sessionfactory.sessions.session.sql.Purpose;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class QueryPurposeEntity extends PurposeEntity {
	public static final QueryPurposeEntity DEFAULT = new QueryPurposeEntity(true, false);

	public QueryPurposeEntity(boolean isGetSqlParameterValues, boolean isGetSqlParameters) {
		super(isGetSqlParameterValues, isGetSqlParameters);
	}
	
	@Override
	public Purpose getPurpose() {
		return Purpose.QUERY;
	}
}
