package com.douglei.orm.dialect.impl.oracle.datatype.handler.dbtype;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.handler.classtype.BlobDataTypeHandler;
import com.douglei.orm.dialect.temp.datatype.handler.dbtype.DBDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class BlobDBDataTypeHandler extends DBDataTypeHandler{
	private static final long serialVersionUID = 9150474452281691060L;
	private BlobDBDataTypeHandler() {}
	private static final BlobDBDataTypeHandler instance = new BlobDBDataTypeHandler();
	public static final BlobDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return com.douglei.orm.dialect.impl.oracle.datatype.db.Blob.singleInstance();
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
