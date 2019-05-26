package com.douglei.database.dialect.impl.mysql.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.AbstractIntegerDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;

/**
 * 
 * @author DougLei
 */
public class IntegerDataTypeHandler extends AbstractIntegerDataTypeHandler{
	private IntegerDataTypeHandler() {}
	private static final IntegerDataTypeHandler instance = new IntegerDataTypeHandler();
	public static final IntegerDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return MySqlDBType.INT.getSqlType();
	}
}
