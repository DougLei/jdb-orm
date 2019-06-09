package com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.Smallint;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.classtype.ShortDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class SmallIntDBDataTypeHandler extends DBDataTypeHandler{
	private SmallIntDBDataTypeHandler() {}
	private static final SmallIntDBDataTypeHandler instance = new SmallIntDBDataTypeHandler();
	public static final SmallIntDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return Smallint.singleInstance().getTypeName();
	}

	@Override
	public int getSqlType() {
		return Smallint.singleInstance().getSqlType();
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
}
