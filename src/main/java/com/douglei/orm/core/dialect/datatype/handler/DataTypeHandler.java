package com.douglei.orm.core.dialect.datatype.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author DougLei
 */
public interface DataTypeHandler {
	
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

	/**
	 * 对value的类型进行验证
	 * @param value
	 * @return
	 */
	DataTypeValidateResult validateValueDataType(Object value);
}