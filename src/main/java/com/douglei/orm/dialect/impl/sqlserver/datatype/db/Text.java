package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Text extends DBDataType{
	private static final Text singleton = new Text();
	public static Text getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Text() {
		super("TEXT", -1);
	}
	
	@Override
	public final String getValue(int columnIndex, ResultSet resultSet) throws SQLException {
		return resultSet.getString(columnIndex);
	}
	
	@Override
	public final String getValue(int parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getString(parameterIndex);
	}
}
