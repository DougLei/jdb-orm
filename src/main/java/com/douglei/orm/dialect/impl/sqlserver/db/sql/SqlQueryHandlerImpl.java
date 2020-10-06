package com.douglei.orm.dialect.impl.sqlserver.db.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.dialect.sql.SqlQueryConnection;
import com.douglei.orm.dialect.sql.SqlQueryHandler;
import com.douglei.orm.dialect.sql.SqlStatementHandler;

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
	public String queryViewScript(String viewName, SqlQueryConnection connection) throws SQLException {
		return queryScript(viewName, sqlStatementHandler.queryViewScript(), connection, "视图");
	}

	@Override
	public String queryProcedureScript(String procName, SqlQueryConnection connection) throws SQLException {
		return queryScript(procName, sqlStatementHandler.queryProcedureScript(), connection, "存储过程");
	}
	
	private String queryScript(String name, String sql, SqlQueryConnection connection, String desc) throws SQLException {
		PreparedStatement pst = connection.getPreparedStatement(sql);
		pst.setString(1, name);
		
		ResultSet rs = pst.executeQuery();
		String script = null;
		if(rs.next())
			script = rs.getString(1);
		
		logger.debug("从数据库中查询到的, 名为{}的{}脚本为: {}", name, desc, script);
		return script;
	}
}
