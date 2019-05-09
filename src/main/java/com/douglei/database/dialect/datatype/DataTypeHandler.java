package com.douglei.database.dialect.datatype;

import java.sql.PreparedStatement;
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
	 * 设置值
	 * @param preparedStatement
	 * @param parameterIndex
	 * @param value
	 * @throws SQLException
	 */
	abstract void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException;
	
	/**
	 * 将值格式化成对应的数据类型
	 * @param value
	 * @return
	 */
	abstract Object turnValueToTargetDataType(Object value);
}
