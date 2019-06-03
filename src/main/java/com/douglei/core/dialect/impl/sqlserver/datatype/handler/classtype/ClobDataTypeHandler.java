package com.douglei.core.dialect.impl.sqlserver.datatype.handler.classtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.core.dialect.datatype.DBDataType;
import com.douglei.core.dialect.datatype.handler.classtype.AbstractClobDataTypeHandler;
import com.douglei.core.dialect.impl.sqlserver.datatype.Varcharmax;

/**
 * 
 * @author DougLei
 */
public class ClobDataTypeHandler extends AbstractClobDataTypeHandler{
	private ClobDataTypeHandler() {}
	private static final ClobDataTypeHandler instance = new ClobDataTypeHandler();
	public static final ClobDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		StringDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}
	
	@Override
	public DBDataType defaultDBDataType() {
		return Varcharmax.singleInstance();
	}
}
