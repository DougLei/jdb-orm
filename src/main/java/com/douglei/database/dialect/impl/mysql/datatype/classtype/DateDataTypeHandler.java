package com.douglei.database.dialect.impl.mysql.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.AbstractDateDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;

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
		return MySqlDBType.DATETIME.getSqlType();
	}
}
