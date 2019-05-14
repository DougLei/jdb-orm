package com.douglei.database.dialect.impl.oracle.datatype.ormtype;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.ormtype.OrmDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class StringOrmDataTypeHandler extends OrmDataTypeHandler{

	@Override
	public String getCode() {
		return "string";
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(value == null) {
			preparedStatement.setNull(parameterIndex, 12);
		}else {
			preparedStatement.setString(parameterIndex, value.toString());
		}
	}
}
