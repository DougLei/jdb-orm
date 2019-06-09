package com.douglei.orm.core.dialect.impl.oracle.datatype.handler;

import com.douglei.orm.core.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.oracle.datatype.Varchar2;

/**
 * oracle datatype handler mapping
 * @author DougLei
 */
public class DataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {

	@Override
	public DBDataTypeHandler getDefaultDBDataTypeHandler() {
		return getDBDataTypeHandlerByDBTypeName(Varchar2.singleInstance().getTypeName());
	}
}
