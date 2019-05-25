package com.douglei.database.dialect.impl.mysql.datatype;

import com.douglei.database.dialect.datatype.AbstractDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;

/**
 * mysql datatype handler mapping
 * @author DougLei
 */
public class MySqlDataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {

	@Override
	public DBDataTypeHandler getDefaultDBDataTypeHandler() {
		return getDBDataTypeHandlerByDBTypeName("varchar");
	}
}
