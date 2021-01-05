package com.douglei.orm.sessionfactory.sessions.session.sql.impl.purpose;

import com.douglei.orm.sessionfactory.sessions.session.sql.Purpose;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class UpdatePurposeEntity extends PurposeEntity {
	public static final UpdatePurposeEntity singleton = new UpdatePurposeEntity();
	public static UpdatePurposeEntity getSingleton() {
		return singleton;
	}
	
	private UpdatePurposeEntity() {
		super(false, true);
	}
	
	@Override
	public Purpose getPurpose() {
		return Purpose.UPDATE;
	}
}
