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
	 * @param index
	 * @param value
	 * @throws SQLException
	 */
	void setValue(PreparedStatement preparedStatement, int index, Object value) throws SQLException;
	
	/**
	 * 获取值
	 * @param resultSet
	 * @param index
	 * @return
	 * @throws SQLException
	 */
	Object getValue(ResultSet resultSet, int index) throws SQLException;
}
