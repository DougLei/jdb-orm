package com.douglei.orm.mapping.handler.object;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.dialect.sqlhandler.SqlStatementHandler;
import com.douglei.orm.mapping.handler.MappingHandleException;
import com.douglei.tools.ExceptionUtil;

/**
 * 
 * @author DougLei
 */
public class DBConnection {
	private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);
	
	private Connection connection;
	private SqlStatementHandler SQLStatementHandler;
	
	public DBConnection(DataSourceWrapper dataSource, SqlStatementHandler SQLStatementHandler){
		this.connection = dataSource.getConnection(false).getConnection();
		this.SQLStatementHandler = SQLStatementHandler;
	}

	// -------------------------------------------------------------------------------------
	//执行SQL语句
	public void executeSql(String sql) throws SQLException {
		logger.debug("执行的SQL语句为: {}", sql);
		try(Statement statement = connection.createStatement()) {
			statement.execute(sql);
		}
	}
	
	// -------------------------------------------------------------------------------------
	// 查询xx是否存在的公共方法
	private boolean queryExists(PreparedStatement ps, String value) throws SQLException {
		ps.setString(1, value);
		ResultSet resultSet = ps.executeQuery();
		if(resultSet.next())
			return Byte.parseByte(resultSet.getObject(1).toString()) == 1;
		throw new MappingHandleException("ResultSet对象中没有任何数据, 请检查sql语句是否正确");
	}
	
	// 查询判断表是否存在
	private PreparedStatement tableExists;
	public boolean tableExists(String tableName) throws SQLException {
		if(tableExists == null)
			tableExists = connection.prepareStatement(SQLStatementHandler.queryTableExists());
		return queryExists(tableExists, tableName);
	}
	
	// 查询判断视图是否存在
	private PreparedStatement viewExists;
	public boolean viewExists(String name) throws SQLException {
		if(viewExists == null)
			viewExists = connection.prepareStatement(SQLStatementHandler.queryViewExists());
		return queryExists(viewExists, name);
	}
	
	// 查询判断存储过程是否存在
	private PreparedStatement procedureExists;
	public boolean procedureExists(String name) throws SQLException {
		if(procedureExists == null)
			procedureExists = connection.prepareStatement(SQLStatementHandler.queryProcedureExists());
		return queryExists(procedureExists, name);
	}
	
	// -------------------------------------------------------------------------------------
	// 关闭
	public void close() {
		if(tableExists != null) {
			try {
				tableExists.close();
				tableExists = null;
			} catch (SQLException e) {
				logger.error("关闭查询表是否存在的PreparedStatement实例时出现异常: {}", ExceptionUtil.getStackTrace(e));
			}
		}
		
		if(viewExists != null) {
			try {
				viewExists.close();
				viewExists = null;
			} catch (SQLException e) {
				logger.error("关闭查询视图是否存在的PreparedStatement实例时出现异常: {}", ExceptionUtil.getStackTrace(e));
			}
		}
		
		if(procedureExists != null) {
			try {
				procedureExists.close();
				procedureExists = null;
			} catch (SQLException e) {
				logger.error("关闭查询存储过程是否存在的PreparedStatement实例时出现异常: {}", ExceptionUtil.getStackTrace(e));
			}
		}
		
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("关闭MappingHandler中使用的Connection实例时出现异常: {}", ExceptionUtil.getStackTrace(e));
			}
		}
	}
}
