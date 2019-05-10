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
	 * <pre>
	 * 	支持处理的Class
	 * 	可以通过Object value, 获取对应的DataTypeHandler
	 * </pre>
	 * @return
	 */
	public abstract Class<?> supportClass();
	
	/**
	 * <pre>
	 * 	支持处理的ColumnType
	 * 	可以通过java.sql.ResultSet中结果集列的元数据ColumnType, 获取对应的DataTypeHandler
	 * </pre>
	 * @return
	 */
	public abstract int supportColumnType();
	
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
	
	@Override
	public String toString() {
		return getClass().getName() + " [code="+getCode()+", supportClass="+supportClass()+", supportColumnType="+supportColumnType() + "]";
	}
}
