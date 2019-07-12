package com.douglei.orm.core.dialect.impl.mysql.db.object;

import java.util.List;

import com.douglei.orm.core.dialect.db.object.DBObjectHandler;
import com.douglei.orm.core.dialect.db.object.DBObjectType;

/**
 * 
 * @author DougLei
 */
public class DBObjectHandlerImpl extends DBObjectHandler {

	@Override
	protected short nameMaxLength() {
		return 64;
	}

	@Override
	public String getQueryDBObjectIsExistsSqlStatement(DBObjectType dbObjectType, String dbObjectName, List<Object> parameters) {
		// TODO Auto-generated method stub
		
		
		
		return null;
	}
}
