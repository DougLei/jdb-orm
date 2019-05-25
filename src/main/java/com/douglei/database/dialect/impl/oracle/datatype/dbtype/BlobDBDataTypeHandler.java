package com.douglei.database.dialect.impl.oracle.datatype.dbtype;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.classtype.impl.BlobDataTypeHandler;
import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.utils.CloseUtil;

/**
 * 
 * @author DougLei
 */
class BlobDBDataTypeHandler extends DBDataTypeHandler{
	
	@Override
	public String getTypeName() {
		return "blob";
	}
	
	@Override
	public int getSqlType() {
		return 2004;
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
		InputStream input = blob.getBinaryStream();
		if(input == null) {
			return null;
		}
		
		ByteArrayOutputStream output = null;
		try {
			output = new ByteArrayOutputStream();
			int length;
			byte[] b = new byte[1024];
			while((length = input.read(b)) != -1) {
				output.write(b, 0, length);
			}
			return output.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException("读取blob类型的数据时出现异常", e);
		} finally {
			CloseUtil.closeDBConn(input, output);
		}
	}
}
