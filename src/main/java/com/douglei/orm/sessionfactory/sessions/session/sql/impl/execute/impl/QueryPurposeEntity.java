package com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.impl;

import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.Purpose;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class QueryPurposeEntity extends PurposeEntity {
	public static final QueryPurposeEntity DEFAULT = new QueryPurposeEntity(false, true);

	public QueryPurposeEntity(boolean getSqlParameters, boolean getSqlParameterValues) {
		super(getSqlParameters, getSqlParameterValues);
	}
	
	@Override
	public Purpose getPurpose() {
		return Purpose.QUERY;
	}
}
