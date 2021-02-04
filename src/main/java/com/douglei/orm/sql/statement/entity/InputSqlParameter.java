package com.douglei.orm.sql.statement.entity;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.orm.dialect.datatype.db.DBDataType;

/**
 * 输入的sql参数对象
 * @author DougLei
 */
public class InputSqlParameter {
	private Object value;
	private DBDataType dbDataType;
	
	public InputSqlParameter(Object value) {
		this.value = value;
		this.dbDataType = EnvironmentContext.getDialect().getDataTypeContainer().getDBDataTypeByObject(value);
	}
	public InputSqlParameter(Object value, DBDataType dbDataType) {
		this.value = value;
		this.dbDataType = dbDataType;
	}
	
	/**
	 * 
	 * @param index
	 * @param preparedStatement
	 * @throws SQLException
	 */
	public void setValue(int index, PreparedStatement preparedStatement) throws SQLException {
		dbDataType.setValue(preparedStatement, index, value);
	}
	
	@Override
	public String toString() {
		return "{value=" + value + ", dbDataType=" + dbDataType.getName() + "}";
	}
}
