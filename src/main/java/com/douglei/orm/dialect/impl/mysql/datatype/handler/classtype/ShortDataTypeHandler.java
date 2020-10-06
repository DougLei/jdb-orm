package com.douglei.orm.dialect.impl.mysql.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Smallint;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractShortDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class ShortDataTypeHandler extends AbstractShortDataTypeHandler{
	private static final long serialVersionUID = 1576716090425071692L;
	private ShortDataTypeHandler() {}
	private static final ShortDataTypeHandler instance = new ShortDataTypeHandler();
	public static final ShortDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Smallint.singleInstance();
	}
}
