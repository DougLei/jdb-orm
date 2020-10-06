package com.douglei.orm.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Number;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractByteDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class ByteDataTypeHandler extends AbstractByteDataTypeHandler {
	private static final long serialVersionUID = -7318833583179726994L;
	private ByteDataTypeHandler() {}
	private static final ByteDataTypeHandler instance = new ByteDataTypeHandler();
	public static final ByteDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Number.singleInstance();
	}
}
