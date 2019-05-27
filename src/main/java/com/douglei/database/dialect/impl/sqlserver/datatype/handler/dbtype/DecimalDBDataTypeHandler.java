package com.douglei.database.dialect.impl.sqlserver.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.Decimal;
import com.douglei.database.dialect.impl.sqlserver.datatype.handler.classtype.DoubleDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class DecimalDBDataTypeHandler extends DBDataTypeHandler{
	private DecimalDBDataTypeHandler() {}
	private static final DecimalDBDataTypeHandler instance = new DecimalDBDataTypeHandler();
	public static final DecimalDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return Decimal.singleInstance().getTypeName();
	}

	@Override
	public int getSqlType() {
		return Decimal.singleInstance().getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		DoubleDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getDouble(parameterIndex);
	}

	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return rs.getDouble(columnIndex);
	}
}
