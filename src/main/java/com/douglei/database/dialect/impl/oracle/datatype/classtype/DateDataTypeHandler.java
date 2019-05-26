package com.douglei.database.dialect.impl.oracle.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.AbstractDateDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;

/**
 * 
 * @author DougLei
 */
public class DateDataTypeHandler extends AbstractDateDataTypeHandler{
	private DateDataTypeHandler() {}
	private static final DateDataTypeHandler instance = new DateDataTypeHandler();
	public static final DateDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return OracleDBType.DATE.getSqlType();
	}
}
