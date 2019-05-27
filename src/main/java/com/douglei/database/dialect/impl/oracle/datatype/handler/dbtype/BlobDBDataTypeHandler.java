package com.douglei.database.dialect.impl.oracle.datatype.handler.dbtype;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.handler.OracleDBType;
import com.douglei.database.dialect.impl.oracle.datatype.handler.classtype.BlobDataTypeHandler;

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
		return OracleDBType.BLOB.getTypeName();
	}
	
	@Override
	public int getSqlType() {
		return OracleDBType.BLOB.getSqlType();
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
}
