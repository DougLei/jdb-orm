package com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.impl;

import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.Purpose;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class UnknowPurposeEntity extends PurposeEntity {
	public static final UnknowPurposeEntity DEFAULT = new UnknowPurposeEntity(true, true);

	public UnknowPurposeEntity(boolean getSqlParameters, boolean getSqlParameterValues) {
		super(getSqlParameters, getSqlParameterValues);
	}
	
	@Override
	public Purpose getPurpose() {
		return Purpose.UNKNOW;
	}
}
