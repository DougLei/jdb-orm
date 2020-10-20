package com.douglei.orm.dialect.impl.mysql.datatype.db;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.tools.utils.datatype.dateformat.DateFormatUtil;

/**
 * 
 * @author DougLei
 */
public class Date extends DBDataType {
	private static final long serialVersionUID = -3578773401783487238L;
	private static final Date singleton = new Date();
	public static Date getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	protected Date() {
		super(91);
	}
	
	@Override
	protected void setValue_(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(DateFormatUtil.verifyIsDate(value)) {
			preparedStatement.setDate(parameterIndex, DateFormatUtil.parseSqlDate(value));
		} else {
			super.setValue_(preparedStatement, parameterIndex, value);
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
		if(value instanceof Date || DateFormatUtil.verifyIsDate(value)) 
			return null;
		return new ValidationResult(name, "数据值类型错误, 应为日期类型", "jdb.data.validator.value.datatype.error.date");
	}
}
