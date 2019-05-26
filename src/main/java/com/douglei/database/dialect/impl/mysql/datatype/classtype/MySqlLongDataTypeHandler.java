package com.douglei.database.dialect.impl.mysql.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.LongDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;

/**
 * 
 * @author DougLei
 */
public class MySqlLongDataTypeHandler extends LongDataTypeHandler{
	private MySqlLongDataTypeHandler() {}
	private static final MySqlLongDataTypeHandler instance = new MySqlLongDataTypeHandler();
	public static final MySqlLongDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return MySqlDBType.BIGINT.getSqlType();
	}
}
