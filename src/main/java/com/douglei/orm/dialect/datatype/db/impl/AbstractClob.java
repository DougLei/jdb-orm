package com.douglei.orm.dialect.datatype.db.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.db.wrapper.ClobWrapper;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.tools.utils.CloseUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractClob extends DBDataType {

	protected AbstractClob(int sqlType) {
		super(sqlType);
	}
	
	@Override
	public final Class<?>[] supportClasses() {
		return new Class<?>[] {ClobWrapper.class};
	}

	@Override
	protected final void setValue_(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		if(!(value instanceof String))
			value = value.toString();
		String clob = (String)value;
		if(clob == null) { // {@link ClobWrapper.toString()} 的值可能为null
			preparedStatement.setNull(parameterIndex, sqlType);
		}else {
			preparedStatement.setCharacterStream(parameterIndex, new StringReader(clob), clob.length());
		}
	}

	@Override
	public final String getValue(int columnIndex, ResultSet resultSet) throws SQLException {
		return getClobValue(resultSet.getCharacterStream(columnIndex));
	}

	@Override
	public final String getValue(int parameterIndex, CallableStatement callableStatement) throws SQLException {
		return getClobValue(callableStatement.getCharacterStream(parameterIndex));
	}
	
	/**
	 * 获取clob 字符串
	 * @param reader
	 * @return
	 */
	private String getClobValue(Reader reader) {
		if(reader == null) 
			return null;
		
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
			throw new ReadDataStreamException("读取大字符(clob)类型的数据时出现异常", e);
		} finally {
			CloseUtil.closeDBConn(reader, writer);
		}
	}

	@Override
	public final ValidationResult validate(String name, Object value, int length, int precision) {
		if(value instanceof String || value.getClass() == char.class || value instanceof Character || value instanceof ClobWrapper) 
			return null;
		return new ValidationResult(name, "数据值类型错误, 应为字符类型", "jdb.data.validator.value.datatype.error.string");
	}
}
