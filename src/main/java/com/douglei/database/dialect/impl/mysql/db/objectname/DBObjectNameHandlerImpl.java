package com.douglei.database.dialect.impl.mysql.db.objectname;

import com.douglei.database.dialect.db.objectname.DBObjectNameHandler;

/**
 * 
 * @author DougLei
 */
public class DBObjectNameHandlerImpl extends DBObjectNameHandler {

	@Override
	protected short nameMaxLength() {
		return 64;
	}
}