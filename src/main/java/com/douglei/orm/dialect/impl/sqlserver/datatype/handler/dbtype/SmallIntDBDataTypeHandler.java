package com.douglei.orm.dialect.impl.sqlserver.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Smallint;
import com.douglei.orm.dialect.impl.sqlserver.datatype.handler.classtype.ShortDataTypeHandler;
import com.douglei.orm.dialect.temp.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class SmallIntDBDataTypeHandler extends DBDataTypeHandler{
	private static final long serialVersionUID = -4524391710865217449L;
	private SmallIntDBDataTypeHandler() {}
	private static final SmallIntDBDataTypeHandler instance = new SmallIntDBDataTypeHandler();
	public static final SmallIntDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Smallint.singleInstance();
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		ShortDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getShort(parameterIndex);
	}

	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return rs.getShort(columnIndex);
	}
	
	@Override
	public ValidationResult doValidate(String validateFieldName, Object value, short length, short precision) {
		return ShortDataTypeHandler.singleInstance().doValidate(validateFieldName, value, length, precision);
	}
}
