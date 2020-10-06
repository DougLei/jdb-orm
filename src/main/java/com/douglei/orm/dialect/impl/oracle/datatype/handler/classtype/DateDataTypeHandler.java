package com.douglei.orm.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Date;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractDateDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class DateDataTypeHandler extends AbstractDateDataTypeHandler{
	private static final long serialVersionUID = -6332418967007466799L;
	private DateDataTypeHandler() {}
	private static final DateDataTypeHandler instance = new DateDataTypeHandler();
	public static final DateDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Date.singleInstance();
	}
}
