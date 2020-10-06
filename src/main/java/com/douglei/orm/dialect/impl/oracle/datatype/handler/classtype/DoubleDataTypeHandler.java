package com.douglei.orm.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Number;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractDoubleDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class DoubleDataTypeHandler extends AbstractDoubleDataTypeHandler{
	private static final long serialVersionUID = 7473705874553609464L;
	private DoubleDataTypeHandler() {}
	private static final DoubleDataTypeHandler instance = new DoubleDataTypeHandler();
	public static final DoubleDataTypeHandler singleInstance() {
		return instance;
	}

	@Override
	public DBDataType getDBDataType() {
		return Number.singleInstance();
	}
}
