package com.douglei.orm.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.db.NChar;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractNCharDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class NCharDataTypeHandler extends AbstractNCharDataTypeHandler{
	private static final long serialVersionUID = 8012209480837226246L;
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
