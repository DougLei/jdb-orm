package com.douglei.orm.dialect.temp.datatype.handler.classtype;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.DataType2;
import com.douglei.orm.dialect.temp.datatype.handler.wrapper.Clob;
import com.douglei.orm.dialect.temp.datatype.handler.wrapper.StringWrapper;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractClobDataTypeHandler extends ClassDataTypeHandler{
	private static final long serialVersionUID = 5503263981279184374L;

	@Override
	public String getCode() {
		return DataType2.CLOB.getName();
	}
	
	@Override
	public Class<?>[] supportClasses(){
		return new Class<?>[] {Clob.class};
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
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
		preparedStatement.setNull(parameterIndex, getSqlType());
	}
}
