package com.douglei.orm.dialect.impl.sqlserver.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.dialect.impl.sqlserver.datatype.Int;
import com.douglei.orm.dialect.impl.sqlserver.datatype.handler.classtype.IntegerDataTypeHandler;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class IntDBDataTypeHandler extends DBDataTypeHandler{
	private static final long serialVersionUID = 2705508791668063580L;
	private IntDBDataTypeHandler() {}
	private static final IntDBDataTypeHandler instance = new IntDBDataTypeHandler();
	public static final IntDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Int.singleInstance();
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		IntegerDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getInt(parameterIndex);
	}

	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return rs.getInt(columnIndex);
	}
	
	@Override
	public ValidationResult doValidate(String validateFieldName, Object value, short length, short precision) {
		return IntegerDataTypeHandler.singleInstance().doValidate(validateFieldName, value, length, precision);
	}
}