package com.douglei.orm.dialect.impl.sqlserver.datatype.handler;

import java.lang.reflect.InvocationTargetException;

import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Varchar;
import com.douglei.orm.dialect.temp.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.orm.dialect.temp.datatype.handler.dbtype.DBDataTypeHandler;

/**
 * sqlserver datatype handler mapping
 * @author DougLei
 */
public class DataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {

	public DataTypeHandlerMapping() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		super();
	}

	@Override
	public DBDataTypeHandler getDefaultDBDataTypeHandler() {
		return getDBDataTypeHandlerByDBTypeName(Varchar.singleInstance().getTypeName());
	}
}
