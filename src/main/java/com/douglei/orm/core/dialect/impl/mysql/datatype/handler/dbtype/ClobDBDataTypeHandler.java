package com.douglei.orm.core.dialect.impl.mysql.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.mysql.datatype.Mediumtext;
import com.douglei.orm.core.dialect.impl.mysql.datatype.handler.classtype.ClobDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class ClobDBDataTypeHandler extends DBDataTypeHandler{
	private ClobDBDataTypeHandler() {}
	private static final ClobDBDataTypeHandler instance = new ClobDBDataTypeHandler();
	public static final ClobDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return Mediumtext.singleInstance().getTypeName();
	}

	@Override
	public int getSqlType() {
		return Mediumtext.singleInstance().getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		ClobDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		return getClobValue(callableStatement.getCharacterStream(parameterIndex));
	}

	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return getClobValue(rs.getCharacterStream(columnIndex));
	}
	
	@Override
	public String doValidate(Object value, short length, short precision) {
		// TODO 
		return null;
	}
}
