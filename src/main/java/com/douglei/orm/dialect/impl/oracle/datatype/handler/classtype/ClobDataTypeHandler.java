package com.douglei.orm.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Clob;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractClobDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class ClobDataTypeHandler extends AbstractClobDataTypeHandler{
	private static final long serialVersionUID = 1573384098777195205L;
	private ClobDataTypeHandler() {}
	private static final ClobDataTypeHandler instance = new ClobDataTypeHandler();
	public static final ClobDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Clob.singleInstance();
	}
}
