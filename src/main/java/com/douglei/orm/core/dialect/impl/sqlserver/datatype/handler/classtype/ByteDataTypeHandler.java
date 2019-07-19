package com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.classtype;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.classtype.AbstractByteDataTypeHandler;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.Smallint;

/**
 * 
 * @author DougLei
 */
public class ByteDataTypeHandler extends AbstractByteDataTypeHandler {
	private static final long serialVersionUID = 2725005271862145861L;
	private ByteDataTypeHandler() {}
	private static final ByteDataTypeHandler instance = new ByteDataTypeHandler();
	public static final ByteDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Smallint.singleInstance();
	}
}
