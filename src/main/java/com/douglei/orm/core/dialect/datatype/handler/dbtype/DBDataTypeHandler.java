package com.douglei.orm.core.dialect.datatype.handler.dbtype;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandlerType;
import com.douglei.orm.core.dialect.datatype.handler.ReadDataStreamException;
import com.douglei.orm.core.metadata.validator.ValidatorResult;
import com.douglei.tools.utils.CloseUtil;

/**
 * 
 * @author DougLei
 */
public abstract class DBDataTypeHandler implements DataTypeHandler, DBDataTypeFeatures{
	private static final long serialVersionUID = -2914541368715153601L;

	/**
	 * 从 {@link CallableStatement} 中获取输出参数的值
	 * @param parameterIndex
	 * @param callableStatement
	 * @return
	 * @throws SQLException
	 */
	public abstract Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException;
	
	@Override
	public ValidatorResult doValidate(String validateFieldName, Object value, short length, short precision) {
		return null;// 默认验证通过
	}
	
	@Override
	public String toString() {
		return getClass().getName() + " getDBDataType=[" + getDBDataType() + "]";
	}

	@Override
	public DataTypeHandlerType getType() {
		return DataTypeHandlerType.DB;
	}
	
	@Override
	public String getCode() {
		return getTypeName();
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
			throw new ReadDataStreamException("读取大字符(clob)类型的数据时出现异常", e);
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
			throw new ReadDataStreamException("读取二进制流(blob)类型的数据时出现异常", e);
		} finally {
			CloseUtil.closeDBConn(input, output);
		}
	}
}
