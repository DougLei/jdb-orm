package com.douglei.orm.dialect.impl.mysql.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Int;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractIntegerDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class IntegerDataTypeHandler extends AbstractIntegerDataTypeHandler{
	private static final long serialVersionUID = -3895363434549209275L;
	private IntegerDataTypeHandler() {}
	private static final IntegerDataTypeHandler instance = new IntegerDataTypeHandler();
	public static final IntegerDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Int.singleInstance();
	}
}
