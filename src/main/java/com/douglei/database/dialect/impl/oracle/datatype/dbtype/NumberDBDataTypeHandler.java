package com.douglei.database.dialect.impl.oracle.datatype.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.classtype.impl.AbstractDoubleDataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.impl.AbstractIntegerDataTypeHandler;
import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;
import com.douglei.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
class NumberDBDataTypeHandler extends DBDataTypeHandler{
	
	@Override
	public String getTypeName() {
		return OracleDBType.NUMBER.getTypeName();
	}
	
	@Override
	public int getSqlType() {
		return OracleDBType.NUMBER.getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		if(ValidationUtil.isDouble(value)) {
			AbstractDoubleDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
		}else if(ValidationUtil.isInteger(value)) {
			AbstractIntegerDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
		}else {
			preparedStatement.setNull(parameterIndex, 2);
		}
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		if(callableStatement.getMetaData().getScale(parameterIndex) == 0) {
			long value = callableStatement.getLong(parameterIndex);
			if(value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
				return (int)value;
			}
			return value;
		}
		return callableStatement.getDouble(parameterIndex);
	}
}
