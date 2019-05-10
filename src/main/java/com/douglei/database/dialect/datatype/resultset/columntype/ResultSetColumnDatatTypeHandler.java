package com.douglei.database.dialect.datatype.resultset.columntype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.DataTypeHandler;

/**
 * 用于处理根据java.sql.ResultSet columnType类型, 获取对应的DataTypeHandler
 * @author DougLei
 */
public abstract class ResultSetColumnDatatTypeHandler implements DataTypeHandler{

	/**
	 * 支持处理的ColumnType类型
	 * @return
	 */
	public abstract int supportColumnType();
	
	@Deprecated
	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
	}
	
	@Override
	public String toString() {
		return getClass().getName() + " supportColumnType=["+supportColumnType()+"]";
	}
}
