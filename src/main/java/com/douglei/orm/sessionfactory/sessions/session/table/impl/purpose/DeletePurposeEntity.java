package com.douglei.orm.sessionfactory.sessions.session.table.impl.purpose;

import com.douglei.orm.sessionfactory.sessions.session.table.Purpose;
import com.douglei.orm.sessionfactory.sessions.session.table.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class DeletePurposeEntity extends PurposeEntity {
	public static final DeletePurposeEntity DEFAULT = new DeletePurposeEntity();

	@Override
	public Purpose getPurpose() {
		return Purpose.DELETE;
	}
}
