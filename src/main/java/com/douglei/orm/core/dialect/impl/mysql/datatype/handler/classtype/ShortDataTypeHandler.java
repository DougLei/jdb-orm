package com.douglei.orm.core.dialect.impl.mysql.datatype.handler.classtype;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.classtype.AbstractShortDataTypeHandler;
import com.douglei.orm.core.dialect.impl.mysql.datatype.Smallint;

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
