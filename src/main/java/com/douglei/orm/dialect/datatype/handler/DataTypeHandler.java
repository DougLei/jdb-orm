package com.douglei.orm.dialect.datatype.handler;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author DougLei
 */
public interface DataTypeHandler extends DataTypeValidator, Serializable{
	
	/**
	 * 获取标识
	 * @return
	 */
	String getCode();
	
	/**
	 * 获取类型
	 * @return
	 */
	DataTypeHandlerType getType();
	
	/**
	 * 给preparedStatement设置对应的参数值
	 * @param preparedStatement
	 * @param parameterIndex
	 * @param value
	 * @throws SQLException
	 */
	void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException;
	
	/**
	 * 从resultset中获取对应的列值
	 * @param columnIndex
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	Object getValue(short columnIndex, ResultSet rs) throws SQLException;
}
