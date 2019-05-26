package com.douglei.database.dialect.impl.mysql.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.StringDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;

/**
 * 
 * @author DougLei
 */
public class MySqlStringDataTypeHandler extends StringDataTypeHandler{
	private MySqlStringDataTypeHandler() {}
	private static final MySqlStringDataTypeHandler instance = new MySqlStringDataTypeHandler();
	public static final MySqlStringDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return MySqlDBType.VARCHAR.getSqlType();
	}
}
