package com.douglei.core.dialect.impl.mysql.db.objectname;

import com.douglei.core.dialect.db.objectname.DBObjectNameHandler;

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
