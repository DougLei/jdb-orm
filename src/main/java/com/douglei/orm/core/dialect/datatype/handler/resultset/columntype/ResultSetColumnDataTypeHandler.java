package com.douglei.orm.core.dialect.datatype.handler.resultset.columntype;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandlerType;
import com.douglei.orm.core.metadata.validator.ValidationResult;

/**
 * 用于处理根据java.sql.ResultSet columnType类型, 获取对应的DataTypeHandler
 * @author DougLei
 */
public abstract class ResultSetColumnDataTypeHandler implements DataTypeHandler{
	private static final long serialVersionUID = -3804325797513630249L;

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
	public String getCode() {
		return getClass().getName() + "为["+getType()+"]类型, 没有code";
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
	public ValidationResult doValidate(String validateFieldName, Object value, short length, short precision) {
		return null;
	}
}
