package com.douglei.database.dialect.impl.mysql.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.DoubleDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;

/**
 * 
 * @author DougLei
 */
public class MySqlDoubleDataTypeHandler extends DoubleDataTypeHandler{
	private MySqlDoubleDataTypeHandler() {}
	private static final MySqlDoubleDataTypeHandler instance = new MySqlDoubleDataTypeHandler();
	public static final MySqlDoubleDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return MySqlDBType.DECIMAL.getSqlType();
	}
}
