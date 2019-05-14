package com.douglei.database.dialect.datatype.classtype;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import com.douglei.database.dialect.datatype.DataTypeHandler;
import com.douglei.database.dialect.datatype.DataTypeHandlerType;

/**
 * 用于处理根据class类型, 获取对应的DataTypeHandler
 * @author DougLei
 */
public abstract class ClassDataTypeHandler implements DataTypeHandler {

	/**
	 * 支持处理的Class类型
	 * @return
	 */
	public abstract Class<?>[] supportClasses();
	
	@Deprecated
	@Override
	public Object getValue(int columnIndex, ResultSet resultSet) throws SQLException {
		return null;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + " supportClasses=["+Arrays.toString(supportClasses())+"]";
	}

	@Override
	public DataTypeHandlerType getType() {
		return DataTypeHandlerType.CLASS;
	}
}
