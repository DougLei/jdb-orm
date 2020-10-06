package com.douglei.orm.dialect.impl.mysql.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.mysql.datatype.db.Decimal;
import com.douglei.orm.dialect.impl.mysql.datatype.handler.classtype.DoubleDataTypeHandler;
import com.douglei.orm.dialect.temp.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class DecimalDBDataTypeHandler extends DBDataTypeHandler{
	private static final long serialVersionUID = -7942294017930099351L;
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
	public ValidationResult doValidate(String validateFieldName, Object value, short length, short precision) {
		return DoubleDataTypeHandler.singleInstance().doValidate(validateFieldName, value, length, precision);
	}
}
