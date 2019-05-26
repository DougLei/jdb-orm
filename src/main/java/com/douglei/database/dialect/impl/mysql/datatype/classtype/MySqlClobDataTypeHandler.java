package com.douglei.database.dialect.impl.mysql.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.ClobDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;

/**
 * 
 * @author DougLei
 */
public class MySqlClobDataTypeHandler extends ClobDataTypeHandler{
	private MySqlClobDataTypeHandler() {}
	private static final MySqlClobDataTypeHandler instance = new MySqlClobDataTypeHandler();
	public static final MySqlClobDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return MySqlDBType.TEXT.getSqlType();
	}
}
