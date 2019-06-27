package com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.dbtype;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.Varbinary;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.classtype.BlobDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class VarbinaryDBDataTypeHandler extends DBDataTypeHandler{
	private static final long serialVersionUID = 7871352001306662321L;
	private VarbinaryDBDataTypeHandler() {}
	private static final VarbinaryDBDataTypeHandler instance = new VarbinaryDBDataTypeHandler();
	public static final VarbinaryDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Varbinary.singleInstance();
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
}
