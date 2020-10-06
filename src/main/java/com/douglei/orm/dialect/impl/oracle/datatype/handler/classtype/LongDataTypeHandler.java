package com.douglei.orm.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Number;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractLongDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class LongDataTypeHandler extends AbstractLongDataTypeHandler{
	private static final long serialVersionUID = -1836117862710512855L;
	private LongDataTypeHandler() {}
	private static final LongDataTypeHandler instance = new LongDataTypeHandler();
	public static final LongDataTypeHandler singleInstance() {
		return instance;
	}

	@Override
	public DBDataType getDBDataType() {
		return Number.singleInstance();
	}
}
