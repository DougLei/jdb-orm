package com.douglei.orm.dialect.impl.mysql.datatype.db;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.mapping.validator.ValidationResult;
import com.douglei.tools.datatype.DataTypeValidateUtil;
import com.douglei.tools.datatype.DateFormatUtil;

/**
 * 
 * @author DougLei
 */
public class Datetime extends DBDataType{
	private static final Datetime singleton = new Datetime();
	public static Datetime getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	protected Datetime() {
		super("DATETIME", 93);
	}
	
	@Override
	public Class<?>[] supportClasses() {
		return new Class<?>[] {Date.class};
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(DataTypeValidateUtil.isDate(value)) {
			preparedStatement.setTimestamp(parameterIndex, DateFormatUtil.parseSqlTimestamp(value));
		} else {
			super.setValue(preparedStatement, parameterIndex, value);
		}
	}
	
	@Override
	public Timestamp getValue(int columnIndex, ResultSet resultSet) throws SQLException {
		return resultSet.getTimestamp(columnIndex);
	}
	
	@Override
	public Timestamp getValue(int parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getTimestamp(parameterIndex);
	}
	
	@Override
	public ValidationResult validate(String name, Object value, int length, int precision) {
		if(value instanceof Date || DataTypeValidateUtil.isDate(value)) 
			return null;
		return new ValidationResult(name, "数据值类型错误, 应为日期类型", "jdb.data.validator.value.datatype.error.date");
	}
}
