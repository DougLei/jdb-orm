package com.douglei.orm.sessionfactory.sessions.session.table.impl.purpose;

import com.douglei.orm.sessionfactory.sessions.session.table.Purpose;
import com.douglei.orm.sessionfactory.sessions.session.table.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class InsertPurposeEntity extends PurposeEntity {
	public static final InsertPurposeEntity DEFAULT = new InsertPurposeEntity();
	
	@Override
	public Purpose getPurpose() {
		return Purpose.INSERT;
	}
}
