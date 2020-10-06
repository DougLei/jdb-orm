package com.douglei.orm.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.db.NVarchar2;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractNStringDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class NStringDataTypeHandler extends AbstractNStringDataTypeHandler{
	private static final long serialVersionUID = -1430888849250803576L;
	private NStringDataTypeHandler() {}
	private static final NStringDataTypeHandler instance = new NStringDataTypeHandler();
	public static final NStringDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return NVarchar2.singleInstance();
	}
}
