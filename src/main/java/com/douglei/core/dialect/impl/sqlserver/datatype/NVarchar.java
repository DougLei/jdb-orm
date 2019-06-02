package com.douglei.core.dialect.impl.sqlserver.datatype;

import com.douglei.core.dialect.datatype.DBDataType;

/**
 * 
 * @author DougLei
 */
public class NVarchar extends DBDataType{
	private static final NVarchar instance = new NVarchar();
	public static final NVarchar singleInstance() {
		return instance;
	}
	
	private NVarchar() {
		super((short)-9, (short)4000);
	}
	
	@Override
	public short fixInputLength(short inputLength) {
		if(inputLength > super.length) {
			return Short.MAX_VALUE;// Short.MAX_VALUE时, 使用nvarchar(max)
		}
		return super.fixInputLength(inputLength);
	}
	
	@Override
	public String getDBType4SqlStatement(short length, short precision) {
		if(length == Short.MAX_VALUE) {
			return getTypeName() + "(max)";
		}
		return super.getDBType4SqlStatement(length, precision);
	}
	
	@Override
	public boolean isCharacterType() {
		return true;
	}
}
