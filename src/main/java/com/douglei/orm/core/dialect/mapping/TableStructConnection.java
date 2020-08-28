package com.douglei.orm.core.dialect.mapping;

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
class TableStructConnection {
	private static final Logger logger = LoggerFactory.getLogger(TableStructConnection.class);
	
	private Connection connection;
	private PreparedStatement preparedStatement;
	
	public TableStructConnection(DataSourceWrapper dataSourceWrapper, String queryTableExistsSql) throws SQLException {
		logger.debug("查询表是否存在的sql语句为: {}", queryTableExistsSql);
		this.connection = dataSourceWrapper.getConnection(false).getConnection();
		this.preparedStatement = connection.prepareStatement(queryTableExistsSql);
	}

	// 查询判断表是否存在
	public boolean tableExists(String tableName) throws SQLException {
		preparedStatement.setString(1, tableName);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next())
			return Byte.parseByte(resultSet.getObject(1).toString()) == 1;
		throw new NullPointerException("查询表是否存在时, ResultSet对象中没有任何数据, 请检查查询表是否存在的sql语句是否正确");
	}
	
	//执行SQL语句
	public void executeSql(String sql) throws SQLException {
		logger.debug("执行的SQL语句为: {}", sql);
		try(Statement statement = connection.createStatement()) {
			statement.execute(sql);
		}
	}
	
	// 关闭
	public void close() {
		CloseUtil.closeDBConn(preparedStatement, connection);
	}
}
