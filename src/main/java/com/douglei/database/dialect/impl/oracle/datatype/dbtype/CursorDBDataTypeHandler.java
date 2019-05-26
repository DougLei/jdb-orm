package com.douglei.database.dialect.impl.oracle.datatype.dbtype;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.douglei.database.dialect.datatype.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;
import com.douglei.database.sql.statement.entity.SqlResultsetMetadata;
import com.douglei.database.utils.ResultSetUtil;
import com.douglei.utils.CloseUtil;

/**
 * 
 * @author DougLei
 */
public class CursorDBDataTypeHandler extends DBDataTypeHandler{
	private CursorDBDataTypeHandler() {}
	private static final CursorDBDataTypeHandler instance = new CursorDBDataTypeHandler();
	public static final CursorDBDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public String getTypeName() {
		return OracleDBType.CURSOR.getTypeName();
	}
	
	@Override
	public int getSqlType() {
		return OracleDBType.CURSOR.getSqlType();
	}

	@Override
	public void setValue(PreparedStatement preparedStatement, short parameterIndex, Object value) throws SQLException {
		// TODO ORACLE set Cursor value
	}

	@Override
	public Object getValue(short parameterIndex, CallableStatement callableStatement) throws SQLException {
		ResultSet rs = null;
		try {
			rs = (ResultSet) callableStatement.getObject(parameterIndex);
			if(rs == null || !rs.next()) {
				return null;
			}
			List<SqlResultsetMetadata> resultsetMetadatas = ResultSetUtil.getSqlResultSetMetadata(rs);
			return ResultSetUtil.getResultSetListMap(resultsetMetadatas, rs);
		} catch(Exception e){
			throw new RuntimeException("获取oracle存储过程, ["+OracleDBType.CURSOR.toString()+"]类型的输出参数数据时, 出现异常", e);
		} finally {
			CloseUtil.closeDBConn(rs);
		}
	}

	@Override
	public Object getValue(short columnIndex, ResultSet rs) throws SQLException {
		// TODO ORACLE get Cursor value by ResultSet
		return null;
	}
}
