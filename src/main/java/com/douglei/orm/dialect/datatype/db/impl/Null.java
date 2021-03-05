package com.douglei.orm.dialect.datatype.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Null extends DBDataType{
	private static final Null singleton = new Null();
	public static Null getSingleton() {
		return singleton;
	}
	
	private Null() {
		super(null, 0);
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		preparedStatement.setObject(parameterIndex, null);
	}
}
