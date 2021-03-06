package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Numeric extends DBDataType{
	
	public Numeric() {
		super("NUMERIC", 2);
	}
	
	@Override
	public final BigDecimal getValue(int columnIndex, ResultSet resultSet) throws SQLException {
		return resultSet.getBigDecimal(columnIndex);
	}
	
	@Override
	public final BigDecimal getValue(int parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getBigDecimal(parameterIndex);
	}
}
