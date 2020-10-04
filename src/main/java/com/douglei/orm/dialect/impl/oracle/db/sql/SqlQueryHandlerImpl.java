package com.douglei.orm.dialect.impl.oracle.db.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.dialect.db.sql.SqlQueryConnection;
import com.douglei.orm.dialect.db.sql.SqlQueryHandler;
import com.douglei.orm.dialect.db.sql.SqlStatementHandler;

/**
 * 
 * @author DougLei
 */
public class SqlQueryHandlerImpl extends SqlQueryHandler {
	private static final Logger logger = LoggerFactory.getLogger(SqlQueryHandlerImpl.class);
	
	public SqlQueryHandlerImpl(SqlStatementHandler sqlStatementHandler) {
		super(sqlStatementHandler);
	}

	@Override
	public String queryViewScript(String viewName, SqlQueryConnection connection) throws SQLException{
		PreparedStatement pst = connection.getPreparedStatement(sqlStatementHandler.queryViewScript());
		pst.setString(1, viewName);
		
		ResultSet rs = pst.executeQuery();
		StringBuilder script = null;
		if(rs.next()) {
			script = new StringBuilder(rs.getInt(1) + viewName.length() + 16);
			script.append("create view ").append(viewName).append(" as ");
			script.append(rs.getString(2));
		}
		
		logger.debug("从数据库中查询到的, 名为{}的视图脚本为: {}", viewName, script);
		return script.toString();
	}

	@Override
	public String queryProcedureScript(String procName, SqlQueryConnection connection) throws SQLException{
		PreparedStatement pst = connection.getPreparedStatement(sqlStatementHandler.queryProcedureScript());
		pst.setString(1, procName);
		
		ResultSet rs = pst.executeQuery();
		StringBuilder script = new StringBuilder(2000);
		script.append("create ");
		while(rs.next())
			script.append(rs.getString(1));
		
		logger.debug("从数据库中查询到的, 名为{}的存储过程脚本为: {}", procName, script);
		return script.toString();
	}
}
