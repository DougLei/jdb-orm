package com.douglei.orm.dialect.impl.sqlserver.datatype.db;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.tools.utils.datatype.dateformat.DateFormatUtil;

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
	
	private Datetime() {
		super(93);
	}
	
	@Override
	public Class<?>[] supportClasses() {
		return new Class<?>[] {Date.class};
	}
	
	@Override
	protected void setValue_(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(DateFormatUtil.verifyIsDate(value)) {
			preparedStatement.setTimestamp(parameterIndex, DateFormatUtil.parseSqlTimestamp(value));
		} else {
			super.setValue_(preparedStatement, parameterIndex, value);
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
		if(value instanceof Date || DateFormatUtil.verifyIsDate(value)) 
			return null;
		return new ValidationResult(name, "数据值类型错误, 应为日期类型", "jdb.data.validator.value.datatype.error.date");
	}
}
