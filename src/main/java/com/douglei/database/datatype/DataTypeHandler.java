package com.douglei.database.datatype;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author DougLei
 */
public interface DataTypeHandler {
	
	/**
	 * 设置值
	 * @param preparedStatement
	 * @param parameterIndex
	 * @param value
	 * @throws SQLException
	 */
	void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException;
	
	/**
	 * 获取值
	 * @param resultSet
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 */
	Object getValue(ResultSet resultSet, int columnIndex) throws SQLException;
}
