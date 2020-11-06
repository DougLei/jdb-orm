package com.douglei.orm.sessionfactory.sessions.session.table.impl.purpose;

import com.douglei.orm.sessionfactory.sessions.session.table.Purpose;
import com.douglei.orm.sessionfactory.sessions.session.table.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class UpdatePurposeEntity extends PurposeEntity {
	public static final UpdatePurposeEntity DEFAULT = new UpdatePurposeEntity();
	
	@Override
	public Purpose getPurpose() {
		return Purpose.UPDATE;
	}
}
