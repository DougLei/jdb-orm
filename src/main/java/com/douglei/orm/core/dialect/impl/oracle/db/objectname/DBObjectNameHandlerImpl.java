package com.douglei.orm.core.dialect.impl.oracle.db.objectname;

import com.douglei.orm.core.dialect.db.objectname.DBObjectNameHandler;

/**
 * 
 * @author DougLei
 */
public class DBObjectNameHandlerImpl extends DBObjectNameHandler {

	@Override
	protected short nameMaxLength() {
		return 30;
	}
}
