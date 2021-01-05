package com.douglei.orm.sessionfactory.sessions.session.sql.impl.purpose;

import com.douglei.orm.sessionfactory.sessions.session.sql.Purpose;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class ProcedurePurposeEntity extends PurposeEntity {
	public static final ProcedurePurposeEntity singleton = new ProcedurePurposeEntity();
	public static ProcedurePurposeEntity getSingleton() {
		return singleton;
	}
	
	private ProcedurePurposeEntity() {
		super(true, false);
	}
	
	@Override
	public Purpose getPurpose() {
		return Purpose.PROCEDURE;
	}
}
