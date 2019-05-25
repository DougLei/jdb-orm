package com.douglei.database.dialect.impl.sqlserver.datatype;

import com.douglei.database.dialect.datatype.AbstractDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.dbtype.VarcharDBDataTypeHandler;

/**
 * sqlserver datatype handler mapping
 * @author DougLei
 */
public class SqlServerDataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {

	@Override
	public DBDataTypeHandler getDefaultDBDataTypeHandler() {
		return VarcharDBDataTypeHandler.singleInstance();
	}
}
