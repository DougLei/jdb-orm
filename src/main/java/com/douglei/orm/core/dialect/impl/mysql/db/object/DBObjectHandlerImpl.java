package com.douglei.orm.core.dialect.impl.mysql.db.object;

import com.douglei.orm.core.dialect.db.object.DBObjectHandler;

/**
 * 
 * @author DougLei
 */
public class DBObjectHandlerImpl extends DBObjectHandler {

	@Override
	protected short nameMaxLength() {
		return 64;
	}
}
