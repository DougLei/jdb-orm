package com.douglei.database.datatype;

import java.sql.PreparedStatement;
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
	 * 将值格式化成对应的数据类型
	 * @param value
	 * @return
	 */
	Object turnValueToTargetDataType(Object value);
}
