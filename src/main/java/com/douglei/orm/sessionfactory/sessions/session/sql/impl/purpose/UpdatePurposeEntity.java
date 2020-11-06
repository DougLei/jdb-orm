package com.douglei.orm.sessionfactory.sessions.session.sql.impl.purpose;

import com.douglei.orm.sessionfactory.sessions.session.sql.Purpose;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

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
