package com.douglei.orm.dialect.impl.mysql.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Smallint;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractByteDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class ByteDataTypeHandler extends AbstractByteDataTypeHandler {
	private static final long serialVersionUID = -4728060881470149074L;
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
