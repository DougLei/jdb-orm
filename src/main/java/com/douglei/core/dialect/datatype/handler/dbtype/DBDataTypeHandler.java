package com.douglei.core.dialect.datatype.handler.dbtype;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.SQLException;

import com.douglei.core.dialect.datatype.handler.DataTypeHandler;
import com.douglei.core.dialect.datatype.handler.DataTypeHandlerType;
import com.douglei.utils.CloseUtil;

/**
 * 
 * @author DougLei
 */
public abstract class DBDataTypeHandler implements DataTypeHandler{
	
	/**
	 * 获取数据库type名称
	 * @return
	 */
	public String getTypeName() {
		return getClass().getName();
	}
	
	/**
	 * 获取数据库type值
	 * @see java.sql.Types
	 * @return
	 */
	public abstract int getSqlType();
	
	/**
	 * 从CallableStatement中获取输出参数的指
	 * @param parameterIndex
	 * @param callableStatement
	 * @return
	 * @throws SQLException
	 */
	public abstract Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException;
	
	@Override
	public String toString() {
		return getClass().getName() + " typeName=[" + getTypeName() + "], sqlType=[" + getSqlType() + "]";
	}

	@Override
	public DataTypeHandlerType getType() {
		return DataTypeHandlerType.DB;
	}
	
	/**
	 * 
	 * @param reader
	 * @return
	 */
	protected Object getClobValue(Reader reader) {
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
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	protected Object getBlobValue(InputStream input) {
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
}
