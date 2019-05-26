package com.douglei.database.dialect.impl.mysql.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.DateDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;

/**
 * 
 * @author DougLei
 */
public class MySqlDateDataTypeHandler extends DateDataTypeHandler{
	private MySqlDateDataTypeHandler() {}
	private static final MySqlDateDataTypeHandler instance = new MySqlDateDataTypeHandler();
	public static final MySqlDateDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return MySqlDBType.DATETIME.getSqlType();
	}
}
