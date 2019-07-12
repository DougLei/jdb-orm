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
		switch(dbObjectType) {
			case PROCEDURE:
				
				
				
				
				break;
			case VIEW:
				parameters.add(dbObjectName);
				return "select count(1) from information_schema.views where table_schema = (select database()) and table_name = ?";
		}
		return null;
	}
}
