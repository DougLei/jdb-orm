package com.douglei.orm.dialect.impl.mysql.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Char;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractNCharDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class NCharDataTypeHandler extends AbstractNCharDataTypeHandler{
	private static final long serialVersionUID = -4048271262213932561L;
	private NCharDataTypeHandler() {}
	private static final NCharDataTypeHandler instance = new NCharDataTypeHandler();
	public static final NCharDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Char.singleInstance();
	}
}
