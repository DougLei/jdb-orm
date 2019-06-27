package com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.NVarchar;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.classtype.StringDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class NVarcharDBDataTypeHandler extends DBDataTypeHandler{
	private static final long serialVersionUID = -2027716619896193684L;
	private NVarcharDBDataTypeHandler() {}
	private static final NVarcharDBDataTypeHandler instance = new NVarcharDBDataTypeHandler();
	public static final NVarcharDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return NVarchar.singleInstance();
	}
	
	@Override
	public boolean isCharacterType() {
		return NVarchar.singleInstance().isCharacterType();
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
	public String doValidate(Object value, short length, short precision) {
		return StringDataTypeHandler.singleInstance().doValidate(value, length, precision);
	}
}
