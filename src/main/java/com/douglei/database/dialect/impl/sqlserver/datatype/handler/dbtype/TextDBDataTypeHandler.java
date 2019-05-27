package com.douglei.database.dialect.impl.sqlserver.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.Text;
import com.douglei.database.dialect.impl.sqlserver.datatype.handler.classtype.ClobDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class TextDBDataTypeHandler extends DBDataTypeHandler{
	private TextDBDataTypeHandler() {}
	private static final TextDBDataTypeHandler instance = new TextDBDataTypeHandler();
	public static final TextDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return Text.singleInstance().getTypeName();
	}

	@Override
	public int getSqlType() {
		return Text.singleInstance().getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		ClobDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return getClobValue(callableStatement.getCharacterStream(parameterIndex));
	}

	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return getClobValue(rs.getCharacterStream(columnIndex));
	}
}
