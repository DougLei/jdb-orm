package com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.impl;

import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.Purpose;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class UpdatePurposeEntity extends PurposeEntity {
	public static final UpdatePurposeEntity DEFAULT = new UpdatePurposeEntity(false, true);
	
	public UpdatePurposeEntity(boolean getSqlParameters, boolean getSqlParameterValues) {
		super(getSqlParameters, getSqlParameterValues);
	}
	
	@Override
	public Purpose getPurpose() {
		return Purpose.UPDATE;
	}
}
