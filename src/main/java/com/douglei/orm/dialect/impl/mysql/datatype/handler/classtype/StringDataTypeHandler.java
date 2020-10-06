package com.douglei.orm.dialect.impl.mysql.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Varchar;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractStringDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class StringDataTypeHandler extends AbstractStringDataTypeHandler{
	private static final long serialVersionUID = -8267937039471102286L;
	private StringDataTypeHandler() {}
	private static final StringDataTypeHandler instance = new StringDataTypeHandler();
	public static final StringDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Varchar.singleInstance();
	}
}
