package com.douglei.orm.dialect.impl.mysql.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Bigint;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractLongDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class LongDataTypeHandler extends AbstractLongDataTypeHandler{
	private static final long serialVersionUID = 846812513577519890L;
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
