package com.douglei.orm.dialect.impl.mysql.datatype.db;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.mapping.validator.ValidationResult;
import com.douglei.tools.datatype.DataTypeValidateUtil;
import com.douglei.tools.datatype.DateFormatUtil;

/**
 * 
 * @author DougLei
 */
public class Date extends DBDataType {
	private static final Date singleton = new Date();
	public static Date getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	protected Date() {
		super("DATE", 91);
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(DataTypeValidateUtil.isDate(value)) {
			preparedStatement.setDate(parameterIndex, DateFormatUtil.parseSqlDate(value));
		} else {
			super.setValue(preparedStatement, parameterIndex, value);
		}
	}

	@Override
	public java.sql.Date getValue(int columnIndex, ResultSet resultSet) throws SQLException {
		return resultSet.getDate(columnIndex);
	}
	
	@Override
	public java.sql.Date getValue(int parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getDate(parameterIndex);
	}
	
	@Override
	public ValidationResult validate(String name, Object value, int length, int precision) {
		if(value instanceof Date || DataTypeValidateUtil.isDate(value)) 
			return null;
		return new ValidationResult(name, "数据值类型错误, 应为日期类型", "jdb.data.validator.value.datatype.error.date");
	}
}
