package com.douglei.orm.core.dialect.impl.mysql.db.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.core.dialect.db.sql.SqlQueryConnection;
import com.douglei.orm.core.dialect.db.sql.SqlQueryHandler;
import com.douglei.orm.core.dialect.db.sql.SqlStatementHandler;

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
		PreparedStatement pst = connection.getPreparedStatement(sqlStatementHandler.queryViewScript());
		pst.setString(1, viewName);
		
		ResultSet rs = pst.executeQuery();
		StringBuilder script = new StringBuilder(viewName.length() + 16);
		script.append("create view ").append(viewName).append(" as ");
		if(rs.next()) 
			script.append(rs.getString(1));
		
		logger.debug("从数据库中查询到的, 名为{}的视图脚本为: {}", viewName, script);
		return script.toString();
	}

	@Override
	public String queryProcedureScript(String procName, SqlQueryConnection connection) throws SQLException {
		Statement st = connection.getStatement();
		ResultSet rs = st.executeQuery(sqlStatementHandler.queryProcedureScript() + procName);
		String script = null;
		if(rs.next())
			script = rs.getString(3);
		script = "create " + script.substring(script.indexOf("PROCEDURE"));
		
		logger.debug("从数据库中查询到的, 名为{}的存储过程脚本为: {}", procName, script);
		return script;
	}
}
