package com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.impl;

import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.Purpose;
import com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class UnknowPurposeEntity extends PurposeEntity {
	public static final UnknowPurposeEntity DEFAULT = new UnknowPurposeEntity();
	
	@Override
	public Purpose getPurpose() {
		return Purpose.UNKNOW;
	}
}
