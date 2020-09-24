package com.douglei.orm.core.mapping.struct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.tools.utils.CloseUtil;

/**
 * 
 * @author DougLei
 */
public class DBConnection {
	private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);
	
	private Connection connection;
	
	public DBConnection(DataSourceWrapper dataSourceWrapper){
		this.connection = dataSourceWrapper.getConnection(false).getConnection();
	}

	// -------------------------------------------------------------------------------------
	private PreparedStatement queryTableExistsPreparedStatement;
	
	// 为表结构处理器初始化
	public void init4TableStructHandler(String queryTableExistsSql) throws SQLException {
		queryTableExistsPreparedStatement = connection.prepareStatement(queryTableExistsSql);
	}
	
	// 查询判断表是否存在
	public boolean tableExists(String tableName) throws SQLException {
		queryTableExistsPreparedStatement.setString(1, tableName);
		ResultSet resultSet = queryTableExistsPreparedStatement.executeQuery();
		if(resultSet.next())
			return Byte.parseByte(resultSet.getObject(1).toString()) == 1;
		throw new NullPointerException("查询表是否存在时, ResultSet对象中没有任何数据, 请检查查询表是否存在的sql语句是否正确");
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
		CloseUtil.closeDBConn(queryTableExistsPreparedStatement, connection);
	}
}
