package com.douglei.orm.core.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.classtype.AbstractByteDataTypeHandler;
import com.douglei.orm.core.dialect.impl.oracle.datatype.Number;

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
