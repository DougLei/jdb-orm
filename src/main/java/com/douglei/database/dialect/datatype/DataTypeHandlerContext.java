package com.douglei.database.dialect.datatype;

import com.douglei.database.dialect.datatype.classtype.impl.StringDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class DataTypeHandlerContext {
	
	/**
	 * 获取默认的DataTypeHandler
	 * @return
	 */
	public static final DataTypeHandler getDefaultDataTypeHandler() {
		return StringDataTypeHandler.singleInstance();
	}
}
