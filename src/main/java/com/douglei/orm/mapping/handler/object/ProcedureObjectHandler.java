package com.douglei.orm.mapping.handler.object;

import java.sql.SQLException;

/**
 * 存储过程对象处理器
 * @author DougLei
 */
public class ProcedureObjectHandler extends ViewObjectHandler{
	
	public ProcedureObjectHandler(DBConnection connection) {
		super(connection);
	}
	
	@Override
	protected boolean exists(String name) throws SQLException {
		return connection.procedureExists(name);
	}
	
	@Override
	protected String getDropSqlStatement(String name) {
		return "drop procedure " + name;
	}
}
