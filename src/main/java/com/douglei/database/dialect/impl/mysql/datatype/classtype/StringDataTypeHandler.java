package com.douglei.database.dialect.impl.mysql.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.AbstractStringDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;

/**
 * 
 * @author DougLei
 */
public class StringDataTypeHandler extends AbstractStringDataTypeHandler{
	private StringDataTypeHandler() {}
	private static final StringDataTypeHandler instance = new StringDataTypeHandler();
	public static final StringDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return MySqlDBType.VARCHAR.getSqlType();
	}
}
