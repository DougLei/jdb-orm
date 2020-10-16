package com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.impl;

import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.Purpose;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class UpdatePurposeEntity extends PurposeEntity {
	public static final UpdatePurposeEntity DEFAULT = new UpdatePurposeEntity(true, false);
	
	public UpdatePurposeEntity(boolean isGetSqlParameterValues, boolean isGetSqlParameters) {
		super(isGetSqlParameterValues, isGetSqlParameters);
	}
	
	@Override
	public Purpose getPurpose() {
		return Purpose.UPDATE;
	}
}
