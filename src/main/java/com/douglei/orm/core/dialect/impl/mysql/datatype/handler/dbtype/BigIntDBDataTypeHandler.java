package com.douglei.orm.core.dialect.impl.mysql.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.mysql.datatype.Bigint;
import com.douglei.orm.core.dialect.impl.mysql.datatype.handler.classtype.LongDataTypeHandler;
import com.douglei.orm.core.metadata.validator.ValidatorResult;

/**
 * 
 * @author DougLei
 */
public class BigIntDBDataTypeHandler extends DBDataTypeHandler{
	private static final long serialVersionUID = 6436806345341700510L;
	private BigIntDBDataTypeHandler() {}
	private static final BigIntDBDataTypeHandler instance = new BigIntDBDataTypeHandler();
	public static final BigIntDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Bigint.singleInstance();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		LongDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return callableStatement.getLong(parameterIndex);
	}

	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return rs.getLong(columnIndex);
	}
	
	@Override
	public ValidatorResult doValidate(Object value, short length, short precision) {
		return LongDataTypeHandler.singleInstance().doValidate(value, length, precision);
	}
}
