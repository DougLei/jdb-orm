package com.douglei.orm.dialect.impl.sqlserver.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.NChar;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractNCharDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class NCharDataTypeHandler extends AbstractNCharDataTypeHandler{
	private static final long serialVersionUID = 2620493343276868136L;
	private NCharDataTypeHandler() {}
	private static final NCharDataTypeHandler instance = new NCharDataTypeHandler();
	public static final NCharDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return NChar.singleInstance();
	}
}
