package com.douglei.orm.dialect.datatype.db.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.mapping.validator.ValidateFailResult;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractBlob extends DBDataType{

	protected AbstractBlob(String name, int sqlType) {
		super(name, sqlType);
	}

	@Override
	public final Class<?>[] supportClasses() {
		return new Class<?>[] {byte[].class, Byte[].class};
	}
	
	@Override
	public final void setValue(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(!(value instanceof byte[])) 
			value = value.toString().getBytes(StandardCharsets.UTF_8);
		byte[] byteArray = (byte[]) value;
		preparedStatement.setBinaryStream(parameterIndex, new ByteArrayInputStream(byteArray), byteArray.length);
	}
	
	@Override
	public final byte[] getValue(int columnIndex, ResultSet resultSet) throws SQLException {
		return getBlobValue(resultSet.getBinaryStream(columnIndex));
	}
	
	@Override
	public final byte[] getValue(int parameterIndex, CallableStatement callableStatement) throws SQLException {
		Blob blob = callableStatement.getBlob(parameterIndex);
		if(blob == null)
			return null;
		return getBlobValue(blob.getBinaryStream());
	}
	
	/**
	 * 获取blob byte数组
	 * @param input
	 * @return
	 */
	private byte[] getBlobValue(InputStream input) {
		if(input == null) 
			return null;
		
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			int length;
			byte[] b = new byte[1024];
			while((length = input.read(b)) != -1) 
				output.write(b, 0, length);
			return output.toByteArray();
		} catch (IOException e) {
			throw new ReadDataStreamException("读取二进制流(blob)类型的数据时出现异常", e);
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				throw new ReadDataStreamException("读取二进制流(blob)类型的数据, 关闭输入流时出现异常", e);
			}
		}
	}
	
	@Override
	public final ValidateFailResult validate(String name, Object value, int length, int precision) {
		if(value instanceof byte[]) 
			return null;
		return new ValidateFailResult(name, "数据值类型错误, 应为字节数组类型", "jdb.data.validator.value.datatype.error.bytearray");
	}
}
