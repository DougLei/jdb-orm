package com.douglei.orm.dialect.impl.mysql.datatype.handler;

import java.lang.reflect.InvocationTargetException;

import com.douglei.orm.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.orm.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.dialect.impl.mysql.datatype.Varchar;

/**
 * mysql datatype handler mapping
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
