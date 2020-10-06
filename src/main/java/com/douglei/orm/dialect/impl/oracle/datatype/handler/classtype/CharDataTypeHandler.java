package com.douglei.orm.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Char;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractCharDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class CharDataTypeHandler extends AbstractCharDataTypeHandler{
	private static final long serialVersionUID = -8140700112105556456L;
	private CharDataTypeHandler() {}
	private static final CharDataTypeHandler instance = new CharDataTypeHandler();
	public static final CharDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Char.singleInstance();
	}
}
