package com.douglei.database.dialect.impl.sqlserver.datatype.handler.classtype;

import com.douglei.database.dialect.datatype.handler.classtype.AbstractDateDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.handler.SqlServerDBType;

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
		return SqlServerDBType.DATETIME.getSqlType();
	}
}
