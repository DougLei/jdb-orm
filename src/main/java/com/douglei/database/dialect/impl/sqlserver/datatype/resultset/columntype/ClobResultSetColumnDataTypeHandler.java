package com.douglei.database.dialect.impl.sqlserver.datatype.resultset.columntype;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;
import com.douglei.utils.CloseUtil;

/**
 * 
 * @author DougLei
 */
public class ClobResultSetColumnDataTypeHandler extends ResultSetColumnDataTypeHandler{
	private static final int[] supportColumnTypes = {
			-1	// text
			};
	
	@Override
	public Object getValue(int columnIndex, ResultSet rs) throws SQLException {
		Reader reader = rs.getCharacterStream(columnIndex);
		if(reader == null) {
			return null;
		}
		
		StringWriter writer = null;
		try {
			writer = new StringWriter();
			int length;
			char[] ch = new char[512];
			while((length = reader.read(ch)) != -1) {
				writer.write(ch, 0, length);
			}
			return writer.toString();
		} catch (IOException e) {
			throw new RuntimeException("读取text类型的数据时出现异常", e);
		} finally {
			CloseUtil.closeDBConn(reader, writer);
		}
	}

	@Override
	public int[] supportColumnTypes() {
		return supportColumnTypes;
	}
}
