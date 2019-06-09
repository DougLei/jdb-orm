package com.douglei.orm.core.dialect.impl.mysql.datatype.handler.classtype;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.classtype.AbstractCharDataTypeHandler;
import com.douglei.orm.core.dialect.impl.mysql.datatype.Char;

/**
 * 
 * @author DougLei
 */
public class CharDataTypeHandler extends AbstractCharDataTypeHandler{
	private CharDataTypeHandler() {}
	private static final CharDataTypeHandler instance = new CharDataTypeHandler();
	public static final CharDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType defaultDBDataType() {
		return Char.singleInstance();
	}
}
