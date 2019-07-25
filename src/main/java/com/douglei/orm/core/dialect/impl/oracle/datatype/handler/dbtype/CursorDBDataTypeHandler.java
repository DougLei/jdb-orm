package com.douglei.orm.core.dialect.impl.oracle.datatype.handler.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.ReadDataStreamException;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.impl.oracle.datatype.Cursor;
import com.douglei.orm.core.utils.ResultSetUtil;
import com.douglei.tools.utils.CloseUtil;

/**
 * 
 * @author DougLei
 */
public class CursorDBDataTypeHandler extends DBDataTypeHandler{
	private static final long serialVersionUID = 5936999043267163096L;
	private CursorDBDataTypeHandler() {}
	private static final CursorDBDataTypeHandler instance = new CursorDBDataTypeHandler();
	public static final CursorDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Cursor.singleInstance();
	}
	
	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		// 不实现set CURSOR value
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		ResultSet rs = null;
		try {
			rs = (ResultSet) callableStatement.getObject(parameterIndex);
			if(rs == null || !rs.next()) {
				return null;
			}
			return ResultSetUtil.getResultSetListMap(rs);
		} catch(SQLException e){
			throw new ReadDataStreamException("获取oracle存储过程, ["+Cursor.singleInstance().toString()+"]类型的输出参数数据时, 出现异常", e);
		} finally {
			CloseUtil.closeDBConn(rs);
		}
	}

	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		// 不实现从ResultSet中获取CURSOR
		return null;
	}
}
