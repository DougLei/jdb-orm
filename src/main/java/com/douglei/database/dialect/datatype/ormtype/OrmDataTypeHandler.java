package com.douglei.database.dialect.datatype.ormtype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.DataTypeHandler;

/**
 * 用于处理映射文件中dataType属性
 * @author DougLei
 */
public abstract class OrmDataTypeHandler implements DataTypeHandler{

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
	
	@Deprecated
	@Override
	public Object getValue(int columnIndex, ResultSet resultSet) throws SQLException {
		return null;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + " code=["+getCode()+"]";
	}
}
