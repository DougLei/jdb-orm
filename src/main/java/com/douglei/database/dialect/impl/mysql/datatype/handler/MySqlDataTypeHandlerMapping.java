package com.douglei.database.dialect.impl.mysql.datatype.handler;

import com.douglei.database.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.handler.dbtype.DBDataTypeHandler;

/**
 * mysql datatype handler mapping
 * @author DougLei
 */
public class MySqlDataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {

	@Override
	public DBDataTypeHandler getDefaultDBDataTypeHandler() {
		return getDBDataTypeHandlerByDBTypeName(MySqlDBType.VARCHAR.getTypeName());
	}
}
