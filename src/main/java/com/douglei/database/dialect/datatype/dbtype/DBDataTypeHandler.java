package com.douglei.database.dialect.datatype.dbtype;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.DataTypeHandler;
import com.douglei.database.dialect.datatype.DataTypeHandlerType;

/**
 * 
 * @author DougLei
 */
public abstract class DBDataTypeHandler implements DataTypeHandler{
	
	/**
	 * 获取数据库type名称
	 * @return
	 */
	public String getTypeName() {
		return getClass().getName();
	}
	
	/**
	 * 获取数据库type值
	 * @see java.sql.Types
	 * @return
	 */
	public abstract int getSqlType();
	
	/**
	 * 从CallableStatement中获取输出参数的指
	 * @param outParameterIndex
	 * @param callableStatement
	 * @return
	 */
	public abstract Object getValue(short outParameterIndex, CallableStatement callableStatement);
	
	@Override
	public String toString() {
		return getClass().getName() + " typeName=[" + getTypeName() + "], sqlType=[" + getSqlType() + "]";
	}

	@Override
	public DataTypeHandlerType getType() {
		return DataTypeHandlerType.DB;
	}
	
	@Deprecated
	@Override
	public Object getValue(short columnIndex, ResultSet resultSet) throws SQLException {
		return null;
	}
}
