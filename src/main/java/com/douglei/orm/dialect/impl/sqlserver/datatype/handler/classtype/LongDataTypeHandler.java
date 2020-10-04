package com.douglei.orm.dialect.impl.sqlserver.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.datatype.handler.classtype.AbstractLongDataTypeHandler;
import com.douglei.orm.dialect.impl.sqlserver.datatype.Bigint;

/**
 * 
 * @author DougLei
 */
public class LongDataTypeHandler extends AbstractLongDataTypeHandler{
	private static final long serialVersionUID = 4692723419820195825L;
	private LongDataTypeHandler() {}
	private static final LongDataTypeHandler instance = new LongDataTypeHandler();
	public static final LongDataTypeHandler singleInstance() {
		return instance;
	}

	@Override
	public DBDataType getDBDataType() {
		return Bigint.singleInstance();
	}
}
