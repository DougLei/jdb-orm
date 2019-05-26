package com.douglei.database.dialect.impl.sqlserver.datatype.resultset.columntype;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.SqlServerDBType;
import com.douglei.utils.CloseUtil;

/**
 * 
 * @author DougLei
 */
class BlobResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private BlobResultSetColumnDataTypeHandler() {}
	private static final BlobResultSetColumnDataTypeHandler instance = new BlobResultSetColumnDataTypeHandler();
	public static final BlobResultSetColumnDataTypeHandler singleInstance() {
		return instance;
	}
	
	private static final int[] supportColumnTypes = {
			SqlServerDBType.VARBINARY.getSqlType()	// varbinary 
			};
	
	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		InputStream input = rs.getBinaryStream(columnIndex);
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
			throw new RuntimeException("读取varbinary类型的数据时出现异常", e);
		} finally {
			CloseUtil.closeDBConn(input, output);
		}
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
