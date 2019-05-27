package com.douglei.database.dialect.impl.sqlserver.datatype.handler.classtype;

import com.douglei.database.dialect.datatype.handler.classtype.AbstractDoubleDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.handler.SqlServerDBType;

/**
 * 
 * @author DougLei
 */
public class DoubleDataTypeHandler extends AbstractDoubleDataTypeHandler{
	private DoubleDataTypeHandler() {}
	private static final DoubleDataTypeHandler instance = new DoubleDataTypeHandler();
	public static final DoubleDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return SqlServerDBType.DECIMAL.getSqlType();
	}
}
