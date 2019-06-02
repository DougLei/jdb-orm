package com.douglei.core.dialect.impl.sqlserver.datatype;

import com.douglei.core.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class NVarcharmax extends DBDataType{
	private static final NVarcharmax instance = new NVarcharmax();
	public static final NVarcharmax singleInstance() {
		return instance;
	}
	
	private NVarcharmax() {// TODO 看看varchar(max)和nvarchar(max)的sqltype值
		super((short)12);
	}
	
	@Override
	public String getTypeName() {
		if(unProcessTypeName) {
			typeName = "NVARCHAR";
			unProcessTypeName = false;
		}
		return typeName;
	}
	private boolean unProcessTypeName=true;
	
	@Override
	public String getDBType4SqlStatement(short length, short precision) {
		return getTypeName() + "(max)";
	}
}
