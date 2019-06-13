package com.douglei.orm.core.dialect.impl.oracle.datatype.handler.dbtype;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.oracle.datatype.handler.classtype.BlobDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class BlobDBDataTypeHandler extends DBDataTypeHandler{
	private BlobDBDataTypeHandler() {}
	private static final BlobDBDataTypeHandler instance = new BlobDBDataTypeHandler();
	public static final BlobDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return com.douglei.orm.core.dialect.impl.oracle.datatype.Blob.singleInstance().getTypeName();
	}
	
	@Override
	public int getSqlType() {
		return com.douglei.orm.core.dialect.impl.oracle.datatype.Blob.singleInstance().getSqlType();
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
		return rs.getBinaryStream(columnIndex);
	}
	
	@Override
	public String doValidate(Object value, short length, short precision) {
		// TODO 
		return null;
	}
}
