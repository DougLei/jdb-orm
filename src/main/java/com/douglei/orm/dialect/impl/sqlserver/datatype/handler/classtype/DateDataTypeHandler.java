package com.douglei.orm.dialect.impl.sqlserver.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Datetime;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractDateDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class DateDataTypeHandler extends AbstractDateDataTypeHandler{
	private static final long serialVersionUID = -954488092603551095L;
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
