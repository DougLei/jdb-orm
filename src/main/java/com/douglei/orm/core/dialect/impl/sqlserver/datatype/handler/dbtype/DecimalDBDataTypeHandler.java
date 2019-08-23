package com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.Decimal;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.classtype.DoubleDataTypeHandler;
import com.douglei.orm.core.metadata.validator.ValidatorResult;

/**
 * 
 * @author DougLei
 */
public class DecimalDBDataTypeHandler extends DBDataTypeHandler{
	private static final long serialVersionUID = 6534453551652579672L;
	private DecimalDBDataTypeHandler() {}
	private static final DecimalDBDataTypeHandler instance = new DecimalDBDataTypeHandler();
	public static final DecimalDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Decimal.singleInstance();
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
	
	@Override
	public ValidatorResult doValidate(String validateFieldName, Object value, short length, short precision) {
		return DoubleDataTypeHandler.singleInstance().doValidate(validateFieldName, value, length, precision);
	}
}
