package com.douglei.orm.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Number;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractShortDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class ShortDataTypeHandler extends AbstractShortDataTypeHandler{
	private static final long serialVersionUID = -8824293606005383495L;
	private ShortDataTypeHandler() {}
	private static final ShortDataTypeHandler instance = new ShortDataTypeHandler();
	public static final ShortDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Number.singleInstance();
	}
}
