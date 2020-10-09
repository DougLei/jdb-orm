package com.douglei.orm.dialect.datatype.db.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 
 * @author DougLei
 */
public class Null extends DBDataType{
	private static final long serialVersionUID = 6267071556036491574L;
	private static final Null singleton = new Null();
	public static Null getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Null() {
		super(0);
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		preparedStatement.setObject(parameterIndex, null);
	}
}
