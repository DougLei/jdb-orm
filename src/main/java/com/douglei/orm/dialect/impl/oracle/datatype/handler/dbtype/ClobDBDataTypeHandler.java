package com.douglei.orm.dialect.impl.oracle.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Clob;
import com.douglei.orm.dialect.impl.oracle.datatype.handler.classtype.ClobDataTypeHandler;
import com.douglei.orm.dialect.temp.datatype.handler.dbtype.DBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class ClobDBDataTypeHandler extends DBDataTypeHandler{
	private static final long serialVersionUID = 6452189201654668877L;
	private ClobDBDataTypeHandler() {}
	private static final ClobDBDataTypeHandler instance = new ClobDBDataTypeHandler();
	public static final ClobDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Clob.singleInstance();
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
}
