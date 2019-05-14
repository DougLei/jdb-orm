package com.douglei.database.dialect.impl.oracle.datatype.classtype;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandler;
import com.douglei.database.dialect.datatype.wrapper.Clob;
import com.douglei.database.dialect.datatype.wrapper.StringWrapper;

/**
 * 
 * @author DougLei
 */
public class ClobDataTypeHandler extends ClassDataTypeHandler{

	@Override
	public String getCode() {
		return "clob";
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return supportClasses;
	}
	private static final Class<?>[] supportClasses = {Clob.class};

	@Override
	public void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(value == null) {
			setNullClob(preparedStatement, parameterIndex);
		}else {
			String clobContent = value.toString();
			if(value instanceof StringWrapper) {
				if(clobContent == null) {
					setNullClob(preparedStatement, parameterIndex);
					return;
				}
			}
			preparedStatement.setCharacterStream(parameterIndex, new StringReader(clobContent), clobContent.length());
		}
	}
	
	private void setNullClob(PreparedStatement preparedStatement, int parameterIndex) throws SQLException {
		preparedStatement.setNull(parameterIndex, 2005);
	}
}
