package com.douglei.orm.dialect.impl.oracle.datatype.handler;

import java.lang.reflect.InvocationTargetException;

import com.douglei.orm.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.orm.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.dialect.impl.oracle.datatype.Varchar2;

/**
 * oracle datatype handler mapping
 * @author DougLei
 */
public class DataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {

	public DataTypeHandlerMapping() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		super();
	}

	@Override
	public DBDataTypeHandler getDefaultDBDataTypeHandler() {
		return getDBDataTypeHandlerByDBTypeName(Varchar2.singleInstance().getTypeName());
	}
}
