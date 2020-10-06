package com.douglei.orm.dialect.impl.mysql.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Mediumtext;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractClobDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class ClobDataTypeHandler extends AbstractClobDataTypeHandler{
	private static final long serialVersionUID = -5710009285569075328L;
	private ClobDataTypeHandler() {}
	private static final ClobDataTypeHandler instance = new ClobDataTypeHandler();
	public static final ClobDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Mediumtext.singleInstance();
	}
}
