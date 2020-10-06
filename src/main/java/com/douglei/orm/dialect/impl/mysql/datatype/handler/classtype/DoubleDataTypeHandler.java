package com.douglei.orm.dialect.impl.mysql.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Decimal;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractDoubleDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class DoubleDataTypeHandler extends AbstractDoubleDataTypeHandler{
	private static final long serialVersionUID = -4067659864579494902L;
	private DoubleDataTypeHandler() {}
	private static final DoubleDataTypeHandler instance = new DoubleDataTypeHandler();
	public static final DoubleDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Decimal.singleInstance();
	}
}
