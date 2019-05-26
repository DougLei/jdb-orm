package com.douglei.database.dialect.impl.mysql.datatype.dbtype;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.MySqlDBType;
import com.douglei.database.dialect.impl.mysql.datatype.classtype.MySqlClobDataTypeHandler;
import com.douglei.utils.CloseUtil;

/**
 * 
 * @author DougLei
 */
class ClobDBDataTypeHandler extends DBDataTypeHandler{
	private ClobDBDataTypeHandler() {}
	private static final ClobDBDataTypeHandler instance = new ClobDBDataTypeHandler();
	public static final ClobDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return MySqlDBType.TEXT.getTypeName();
	}

	@Override
	public int getSqlType() {
		return MySqlDBType.TEXT.getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		MySqlClobDataTypeHandler.singleInstance().setValue(preparedStatement, parameterIndex, value);
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		Reader reader = callableStatement.getCharacterStream(parameterIndex);
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
}
