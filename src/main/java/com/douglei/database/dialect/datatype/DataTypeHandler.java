package com.douglei.database.dialect.datatype;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author DougLei
 */
public abstract class DataTypeHandler {
	
	/**
	 * <pre>
	 * 	获取DataTypeHandler的唯一编码值
	 * 	默认值为类名
	 * </pre>
	 * @return
	 */
	public String getCode() {
		return getClass().getName();
	}
	
	/**
	 * 给preparedStatement设置对应的参数值
	 * @param preparedStatement
	 * @param parameterIndex
	 * @param value
	 * @throws SQLException
	 */
	public abstract void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException;
	
	/**
	 * 从resultset中获取对应的列值
	 * @param columnIndex
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public abstract Object getValue(int columnIndex, ResultSet resultSet)  throws SQLException;
}
