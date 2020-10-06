package com.douglei.orm.dialect.impl.mysql.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Datetime;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractDateDataTypeHandler;

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
