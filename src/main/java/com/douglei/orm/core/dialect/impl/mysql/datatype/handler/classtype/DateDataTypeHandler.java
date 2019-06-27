package com.douglei.orm.core.dialect.impl.mysql.datatype.handler.classtype;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.classtype.AbstractDateDataTypeHandler;
import com.douglei.orm.core.dialect.impl.mysql.datatype.Datetime;

/**
 * 
 * @author DougLei
 */
public class DateDataTypeHandler extends AbstractDateDataTypeHandler{
	private static final long serialVersionUID = 1828231953718101694L;
	private DateDataTypeHandler() {}
	private static final DateDataTypeHandler instance = new DateDataTypeHandler();
	public static final DateDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Datetime.singleInstance();
	}
}
