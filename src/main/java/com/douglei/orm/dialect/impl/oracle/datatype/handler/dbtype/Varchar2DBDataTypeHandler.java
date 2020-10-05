package com.douglei.orm.dialect.impl.oracle.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.dialect.impl.oracle.datatype.Varchar2;
import com.douglei.orm.dialect.impl.oracle.datatype.handler.classtype.StringDataTypeHandler;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class Varchar2DBDataTypeHandler extends DBDataTypeHandler{
	private static final long serialVersionUID = 4589413930412215094L;
	private Varchar2DBDataTypeHandler() {}
	private static final Varchar2DBDataTypeHandler instance = new Varchar2DBDataTypeHandler();
	public static final Varchar2DBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Varchar2.singleInstance();
	}
	
	@Override
	public boolean isCharacterType() {
		return Varchar2.singleInstance().isCharacterType();
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