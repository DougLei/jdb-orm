package com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.dbtype;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.Varbinary;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.classtype.BlobDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class VarbinaryDBDataTypeHandler extends DBDataTypeHandler{
	private VarbinaryDBDataTypeHandler() {}
	private static final VarbinaryDBDataTypeHandler instance = new VarbinaryDBDataTypeHandler();
	public static final VarbinaryDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return Varbinary.singleInstance().getTypeName();
	}

	@Override
	public int getSqlType() {
		return Varbinary.singleInstance().getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		BlobDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		Blob blob = callableStatement.getBlob(parameterIndex);
		if(blob == null) {
			return null;
		}
		return getBlobValue(blob.getBinaryStream());
	}

	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		return getBlobValue(rs.getBinaryStream(columnIndex));
	}
	
	@Override
	public String doValidate(Object value, short length, short precision) {
		// TODO 
		return null;
	}
}
