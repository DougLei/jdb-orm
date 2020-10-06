package com.douglei.orm.dialect.impl.oracle.datatype.handler;

import java.lang.reflect.InvocationTargetException;

import com.douglei.orm.dialect.impl.oracle.datatype.db.Varchar2;
import com.douglei.orm.dialect.temp.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.orm.dialect.temp.datatype.handler.dbtype.DBDataTypeHandler;

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
