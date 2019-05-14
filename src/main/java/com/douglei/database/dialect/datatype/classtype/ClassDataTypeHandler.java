package com.douglei.database.dialect.datatype.classtype;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import com.douglei.database.dialect.datatype.DataTypeHandler;
import com.douglei.database.dialect.datatype.DataTypeHandlerType;

/**
 * 用于处理映射文件中dataType属性
 * @author DougLei
 */
public abstract class ClassDataTypeHandler implements DataTypeHandler{

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
	 * 支持处理的Class类型
	 * @return
	 */
	public Class<?>[] supportClasses(){
		return null;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + " code=["+getCode()+"], supportClasses=["+Arrays.toString(supportClasses())+"]";
	}
	
	@Override
	public DataTypeHandlerType getType() {
		return DataTypeHandlerType.CLASS;
	}
	
	@Deprecated
	@Override
	public Object getValue(int columnIndex, ResultSet resultSet) throws SQLException {
		return null;
	}
}
