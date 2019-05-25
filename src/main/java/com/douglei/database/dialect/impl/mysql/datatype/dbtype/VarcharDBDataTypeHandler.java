package com.douglei.database.dialect.impl.mysql.datatype.dbtype;

import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class VarcharDBDataTypeHandler extends DBDataTypeHandler{
	private VarcharDBDataTypeHandler() {}
	private static final VarcharDBDataTypeHandler instance = new VarcharDBDataTypeHandler();
	public static final VarcharDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return "varchar";
	}

	@Override
	public int getSqlType() {
		// TODO Auto-generated method stub
		return 0;
	}
}
