package com.douglei.database.dialect.impl.oracle.datatype.ormtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.ormtype.OrmDataTypeHandler;
import com.douglei.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public class IntegerOrmDataTypeHandler extends OrmDataTypeHandler{

	@Override
	public String getCode() {
		return "integer";
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(ValidationUtil.isInteger(value)) {
			if(value.getClass() == int.class || value instanceof Integer) {
				preparedStatement.setInt(parameterIndex, (int)value);
			}else {
				preparedStatement.setInt(parameterIndex, Integer.parseInt(value.toString()));
			}
		}else {
			preparedStatement.setNull(parameterIndex, 2);
		}
	}
}
