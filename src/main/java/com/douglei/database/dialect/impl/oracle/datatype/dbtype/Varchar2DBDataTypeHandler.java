package com.douglei.database.dialect.impl.oracle.datatype.dbtype;

import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class Varchar2DBDataTypeHandler extends DBDataTypeHandler{
	private Varchar2DBDataTypeHandler() {}
	private static final Varchar2DBDataTypeHandler instance = new Varchar2DBDataTypeHandler();
	public static final Varchar2DBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return "varchar2";
	}
	
	@Override
	public int getTypeCode() {
		// TODO Auto-generated method stub
		return 0;
	}
}
