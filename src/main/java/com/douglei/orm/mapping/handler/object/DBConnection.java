package com.douglei.orm.mapping.handler.object;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.dialect.sqlhandler.SqlQueryConnection;
import com.douglei.orm.dialect.sqlhandler.SqlQueryHandler;
import com.douglei.orm.dialect.sqlhandler.SqlStatementHandler;
import com.douglei.tools.utils.CloseUtil;

/**
 * 
 * @author DougLei
 */
public class DBConnection {
	private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);
	
	private Connection connection;
	private SqlQueryConnection queryConnection;
	private SqlStatementHandler sqlStatementHandler;
	private SqlQueryHandler sqlQueryHandler;
	
	public DBConnection(DataSourceWrapper dataSourceWrapper, SqlStatementHandler sqlStatementHandler, SqlQueryHandler sqlQueryHandler){
		this.connection = dataSourceWrapper.getConnection(false).getConnection();
		this.sqlStatementHandler = sqlStatementHandler;
		this.sqlQueryHandler = sqlQueryHandler;
	}

	SqlStatementHandler getSqlStatementHandler() {
		return sqlStatementHandler;
	}

	// -------------------------------------------------------------------------------------
	// 查询xx是否存在的公共方法
	private boolean queryExists(PreparedStatement ps, String value) throws SQLException {
		ps.setString(1, value);
		ResultSet resultSet = ps.executeQuery();
		if(resultSet.next())
			return Byte.parseByte(resultSet.getObject(1).toString()) == 1;
		throw new NullPointerException("ResultSet对象中没有任何数据, 请检查sql语句是否正确");
	}
	
	// 查询判断name是否存在
	private PreparedStatement nameExists;
	public boolean nameExists(String name) throws SQLException {
		if(nameExists == null)
			nameExists = connection.prepareStatement(sqlStatementHandler.queryNameExists());
		return queryExists(nameExists, name);
	}
	
	// 查询判断表是否存在
	private PreparedStatement tableExists;
	public boolean tableExists(String tableName) throws SQLException {
		if(tableExists == null)
			tableExists = connection.prepareStatement(sqlStatementHandler.queryTableExists());
		return queryExists(tableExists, tableName);
	}
	
	// 查询判断视图是否存在
	private PreparedStatement viewExists;
	public boolean viewExists(String viewName) throws SQLException {
		if(viewExists == null)
			viewExists = connection.prepareStatement(sqlStatementHandler.queryViewExists());
		return queryExists(viewExists, viewName);
	}
	
	// 查询判断存储过程是否存在
	private PreparedStatement procedureExists;
	public boolean procedureExists(String procName) throws SQLException {
		if(procedureExists == null)
			procedureExists = connection.prepareStatement(sqlStatementHandler.queryProcExists());
		return queryExists(procedureExists, procName);
	}
	
	// 查询获取视图的脚本
	public String queryViewScript(String viewName) throws SQLException {
		if(queryConnection == null)
			queryConnection = new SqlQueryConnection(connection);
		return sqlQueryHandler.queryViewScript(viewName, queryConnection);		
	}
	
	// 查询获取视图的脚本
	public String queryProcedureScript(String procName) throws SQLException {
		if(queryConnection == null)
			queryConnection = new SqlQueryConnection(connection);
		return sqlQueryHandler.queryProcedureScript(procName, queryConnection);		
	}
	
	// -------------------------------------------------------------------------------------
	//执行SQL语句
	public void executeSql(String sql) throws SQLException {
		logger.debug("执行的SQL语句为: {}", sql);
		try(Statement statement = connection.createStatement()) {
			statement.execute(sql);
		}
	}
	
	// 关闭
	public void close() {
		if(queryConnection != null)
			queryConnection.close();
		CloseUtil.closeDBConn(nameExists, tableExists, viewExists, procedureExists, connection);
	}
}
