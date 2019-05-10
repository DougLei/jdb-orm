package com.douglei.database.dialect.datatype.classtype;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.DataTypeHandler;

/**
 * 用于处理根据class类型, 获取对应的DataTypeHandler
 * @author DougLei
 */
public abstract class ClassDataHandler implements DataTypeHandler {

	/**
	 * 支持处理的Class类型
	 * @return
	 */
	public abstract Class<?> supportClass();
	
	@Deprecated
	@Override
	public Object getValue(int columnIndex, ResultSet resultSet) throws SQLException {
		return null;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + " supportClass=["+supportClass()+"]";
	}
}
