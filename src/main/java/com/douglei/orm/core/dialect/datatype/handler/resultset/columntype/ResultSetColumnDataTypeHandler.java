package com.douglei.orm.core.dialect.datatype.handler.resultset.columntype;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandlerType;

/**
 * 用于处理根据java.sql.ResultSet columnType类型, 获取对应的DataTypeHandler
 * @author DougLei
 */
public abstract class ResultSetColumnDataTypeHandler implements DataTypeHandler{
	private static final long serialVersionUID = -315210642935379249L;

	/**
	 * 支持处理的ColumnType类型
	 * @return
	 */
	public abstract int[] supportColumnTypes();
	
	@Override
	public String toString() {
		return getClass().getName() + " supportColumnTypes=["+Arrays.toString(supportColumnTypes())+"]";
	}
	

	@Override
	public DataTypeHandlerType getType() {
		return DataTypeHandlerType.RESULTSET_COLUMN;
	}
	
	@Deprecated
	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
	}

	@Deprecated
	@Override
	public String doValidate(Object value, short length, short precision) {
		return null;
	}
}
