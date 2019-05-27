package com.douglei.database.dialect.impl.sqlserver.datatype.handler;

import com.douglei.database.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.handler.dbtype.DBDataTypeHandler;

/**
 * sqlserver datatype handler mapping
 * @author DougLei
 */
public class SqlServerDataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {

	@Override
	public DBDataTypeHandler getDefaultDBDataTypeHandler() {
		return getDBDataTypeHandlerByDBTypeName(SqlServerDBType.VARCHAR.getTypeName());
	}
}
