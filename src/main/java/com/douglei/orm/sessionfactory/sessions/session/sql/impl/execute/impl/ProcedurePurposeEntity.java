package com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.impl;

import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.Purpose;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class ProcedurePurposeEntity extends PurposeEntity {
	public static final ProcedurePurposeEntity DEFAULT = new ProcedurePurposeEntity(true, false);
	
	public ProcedurePurposeEntity(boolean getSqlParameters, boolean getSqlParameterValues) {
		super(getSqlParameters, getSqlParameterValues);
	}
	
	@Override
	public Purpose getPurpose() {
		return Purpose.PROCEDURE;
	}
}
