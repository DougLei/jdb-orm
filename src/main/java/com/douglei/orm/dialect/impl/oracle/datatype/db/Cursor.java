package com.douglei.orm.dialect.impl.oracle.datatype.db;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.sql.statement.util.ResultSetUtil;

/**
 * 
 * @author DougLei
 */
public class Cursor extends DBDataType{
	private static final Cursor singleton = new Cursor();
	public static Cursor getSingleton() {
		return singleton;
	}
	public Object readResolve() {
		return singleton;
	}
	
	private Cursor() {
		super(-10);
	}
	
	@Override
	public List<Map<String, Object>> getValue(int parameterIndex, CallableStatement callableStatement) throws SQLException {
		try(ResultSet rs = (ResultSet) callableStatement.getObject(parameterIndex)){
			if(rs == null || !rs.next()) 
				return Collections.emptyList();
			return ResultSetUtil.getResultSetListMap(rs);
		}
	}
}
