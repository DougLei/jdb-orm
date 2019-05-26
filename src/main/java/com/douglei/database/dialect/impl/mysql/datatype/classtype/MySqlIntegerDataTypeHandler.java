package com.douglei.database.dialect.impl.mysql.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.IntegerDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;

/**
 * 
 * @author DougLei
 */
public class MySqlIntegerDataTypeHandler extends IntegerDataTypeHandler{
	private MySqlIntegerDataTypeHandler() {}
	private static final MySqlIntegerDataTypeHandler instance = new MySqlIntegerDataTypeHandler();
	public static final MySqlIntegerDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return MySqlDBType.INT.getSqlType();
	}
}
