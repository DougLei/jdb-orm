package com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.Varchar;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.classtype.StringDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class VarcharDBDataTypeHandler extends DBDataTypeHandler{
	private VarcharDBDataTypeHandler() {}
	private static final VarcharDBDataTypeHandler instance = new VarcharDBDataTypeHandler();
	public static final VarcharDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return Varchar.singleInstance().getTypeName();
	}

	@Override
	public int getSqlType() {
		return Varchar.singleInstance().getSqlType();
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
}