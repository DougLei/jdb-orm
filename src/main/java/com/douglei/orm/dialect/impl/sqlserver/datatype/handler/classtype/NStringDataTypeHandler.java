package com.douglei.orm.dialect.impl.sqlserver.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.datatype.handler.classtype.AbstractNStringDataTypeHandler;
import com.douglei.orm.dialect.impl.sqlserver.datatype.NVarchar;

/**
 * 
 * @author DougLei
 */
public class NStringDataTypeHandler extends AbstractNStringDataTypeHandler{
	private static final long serialVersionUID = -7235597191132909673L;
	private NStringDataTypeHandler() {}
	private static final NStringDataTypeHandler instance = new NStringDataTypeHandler();
	public static final NStringDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return NVarchar.singleInstance();
	}
}