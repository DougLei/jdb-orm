package com.douglei.orm.sessionfactory.sessions.session.sql.impl;

import com.douglei.orm.sessionfactory.sessions.session.sql.Purpose;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class UnknowPurposeEntity extends PurposeEntity {
	public static final UnknowPurposeEntity DEFAULT = new UnknowPurposeEntity(true, true);

	public UnknowPurposeEntity(boolean isGetSqlParameterValues, boolean isGetSqlParameters) {
		super(isGetSqlParameterValues, isGetSqlParameters);
	}
	
	@Override
	public Purpose getPurpose() {
		return Purpose.UNKNOW;
	}
}
