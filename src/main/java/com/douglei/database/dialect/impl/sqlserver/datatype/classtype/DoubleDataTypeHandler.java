package com.douglei.database.dialect.impl.sqlserver.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.AbstractDoubleDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.SqlServerDBType;

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
