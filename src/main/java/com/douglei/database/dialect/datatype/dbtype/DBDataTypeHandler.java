package com.douglei.database.dialect.datatype.dbtype;

import java.sql.PreparedStatement;
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
	 * 获取数据库type的code值
	 * @see java.sql.Types
	 * @return
	 */
	public abstract int getTypeCode();
	
	@Override
	public String toString() {
		return getClass().getName() + " typeName=[" + getTypeName() + "], typeCode=[" + getTypeCode() + "]";
	}

	@Override
	public DataTypeHandlerType getType() {
		return DataTypeHandlerType.DB;
	}
	
	@Deprecated
	@Override
	public Object getValue(int columnIndex, ResultSet resultSet) throws SQLException {
		return null;
	}

	@Deprecated
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
	}
}
