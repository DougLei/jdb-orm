package com.douglei.core.dialect.impl.sqlserver.datatype.handler;

import com.douglei.core.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.core.dialect.impl.sqlserver.datatype.Varchar;

/**
 * sqlserver datatype handler mapping
 * @author DougLei
 */
public class DataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {

	@Override
	public DBDataTypeHandler getDefaultDBDataTypeHandler() {
		return getDBDataTypeHandlerByDBTypeName(Varchar.singleInstance().getTypeName());
	}
}