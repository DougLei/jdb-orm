package com.douglei.database.dialect.datatype.classtype.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandler;
import com.douglei.utils.datatype.DateTypeUtil;
import com.douglei.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public class DateDataTypeHandler extends ClassDataTypeHandler{
	private DateDataTypeHandler() {}
	private static final DateDataTypeHandler instance = new DateDataTypeHandler();
	public static final DateDataTypeHandler singleInstance() {
		return instance;
	}

	@Override
	public String getCode() {
		return "date";
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {Date.class};

	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(ValidationUtil.isDate(value)) {
			preparedStatement.setTimestamp(parameterIndex, DateTypeUtil.parseSqlTimestamp(value));
		}else {
			preparedStatement.setNull(parameterIndex, 93);
		}
	}
}
