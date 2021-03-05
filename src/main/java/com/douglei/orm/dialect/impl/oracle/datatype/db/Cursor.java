package com.douglei.orm.dialect.impl.oracle.datatype.db;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.sql.statement.util.ResultSetUtil;

/**
 * 
 * @author DougLei
 */
public class Cursor extends DBDataType{
	
	public Cursor() {
		super("CURSOR", -10);
	}
	
	@Override
	public List<Map<String, Object>> getValue(int parameterIndex, CallableStatement callableStatement) throws SQLException {
		return ResultSetUtil.getListMap((ResultSet) callableStatement.getObject(parameterIndex));
	}
}
