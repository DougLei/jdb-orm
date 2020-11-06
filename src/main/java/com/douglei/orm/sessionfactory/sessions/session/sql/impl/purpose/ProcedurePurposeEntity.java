package com.douglei.orm.sessionfactory.sessions.session.sql.impl.purpose;

import com.douglei.orm.sessionfactory.sessions.session.sql.Purpose;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class ProcedurePurposeEntity extends PurposeEntity {
	public static final ProcedurePurposeEntity DEFAULT = new ProcedurePurposeEntity(false, true);
	
	public ProcedurePurposeEntity(boolean isGetSqlParameterValues, boolean isGetSqlParameters) {
		super(isGetSqlParameterValues, isGetSqlParameters);
	}
	
	@Override
	public Purpose getPurpose() {
		return Purpose.PROCEDURE;
	}
}
