package com.douglei.database.dialect.impl.mysql.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.AbstractClobDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;

/**
 * 
 * @author DougLei
 */
public class ClobDataTypeHandler extends AbstractClobDataTypeHandler{
	private ClobDataTypeHandler() {}
	private static final ClobDataTypeHandler instance = new ClobDataTypeHandler();
	public static final ClobDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return MySqlDBType.TEXT.getSqlType();
	}
}
