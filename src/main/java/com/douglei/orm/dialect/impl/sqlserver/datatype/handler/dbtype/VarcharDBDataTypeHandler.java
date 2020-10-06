package com.douglei.orm.dialect.impl.sqlserver.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Varchar;
import com.douglei.orm.dialect.impl.sqlserver.datatype.handler.classtype.StringDataTypeHandler;
import com.douglei.orm.dialect.temp.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class VarcharDBDataTypeHandler extends DBDataTypeHandler{
	private static final long serialVersionUID = 7204051299887804428L;
	private VarcharDBDataTypeHandler() {}
	private static final VarcharDBDataTypeHandler instance = new VarcharDBDataTypeHandler();
	public static final VarcharDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Varchar.singleInstance();
	}
	
	@Override
	public boolean isCharacterType() {
		return Varchar.singleInstance().isCharacterType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		StringDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getString(parameterIndex);
	}

	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return rs.getString(columnIndex);
	}
	
	@Override
	public ValidationResult doValidate(String validateFieldName, Object value, short length, short precision) {
		return StringDataTypeHandler.singleInstance().doValidate(validateFieldName, value, length, precision);
	}
}
