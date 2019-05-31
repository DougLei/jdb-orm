package com.douglei.core.dialect.db.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.context.DBRunEnvironmentContext;
import com.douglei.core.dialect.db.table.entity.Constraint;
import com.douglei.core.metadata.table.CreateMode;
import com.douglei.core.metadata.table.TableMetadata;
import com.douglei.utils.CloseUtil;
import com.douglei.utils.ExceptionUtil;


/**
 * 
 * @author DougLei
 */
public class TableHandler {
	private static final Logger logger = LoggerFactory.getLogger(TableHandler.class);
	private TableHandler() {}
	private static final TableHandler instance = new TableHandler();
	public static final TableHandler singleInstance() {
		return instance;
	}
	
	/**
	 * 表是否存在
	 * @param tableName
	 * @param preparedStatement
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	private boolean tableExists(String tableName, PreparedStatement preparedStatement, ResultSet rs) throws SQLException {
		preparedStatement.setString(1, tableName);
		rs = preparedStatement.executeQuery();
		if(rs.next()) {
			boolean tableExists = Integer.parseInt(rs.getObject(1).toString()) == 1;
			rs.close();
			return tableExists;
		}
		throw new NullPointerException("查询表是否存在时, ResultSet对象中没有任何数据");
	}
	
	/**
	 * 执行DDL sql语句
	 * @param ddlSQL
	 * @param connection
	 * @param statement
	 * @throws SQLException
	 */
	private void executeDDLSQL(String ddlSQL, Connection connection, Statement statement) throws SQLException {
		statement = connection.createStatement();
		statement.execute(ddlSQL);
		statement.close();
	}
	
	/**
	 * 创建表
	 * @param table
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @throws SQLException 
	 */
	private void createTable(TableMetadata table, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.tableCreateSqlStatement(table), connection, statement);
	}

	/**
	 * 删除表
	 * @param tableName
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @throws SQLException 
	 */
	private void dropTable(String tableName, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.tableDropSqlStatement(tableName), connection, statement);
	}
	
	/**
	 * 创建约束
	 * @param constraints
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @throws SQLException 
	 */
	private void createConstraint(Collection<Constraint> constraints, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler) throws SQLException {
		for (Constraint constraint : constraints) {
			executeDDLSQL(tableSqlStatementHandler.constraintCreateSqlStatement(constraint), connection, statement);
		}
	}
	
	/**
	 * 删除约束
	 * @param constraints
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @throws SQLException 
	 */
	private void dropConstraint(Collection<Constraint> constraints, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler) throws SQLException {
		for (Constraint constraint : constraints) {
			executeDDLSQL(tableSqlStatementHandler.constraintDropSqlStatement(constraint), connection, statement);
		}
	}
	
	/**
	 * create表
	 * @param dataSourceWrapper
	 * @param tables
	 */
	public void create(DataSourceWrapper dataSourceWrapper, List<TableMetadata> tables) {
		TableSqlStatementHandler tableSqlStatementHandler = DBRunEnvironmentContext.getDialect().getTableSqlStatementHandler();
		
		Connection connection = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			connection = dataSourceWrapper.getConnection(false).getConnection();
			preparedStatement = connection.prepareStatement(tableSqlStatementHandler.tableExistsQueryPreparedSqlStatement());
			
			for (TableMetadata table : tables) {
				if(tableExists(table.getName(), preparedStatement, rs) && table.getCreateMode() == CreateMode.DROP_CREATE) {
					dropConstraint(table.getConstraints(), connection, statement, tableSqlStatementHandler);
					dropTable(table.getName(), connection, statement, tableSqlStatementHandler);
				}
				createTable(table, connection, statement, tableSqlStatementHandler);
				createConstraint(table.getConstraints(), connection, statement, tableSqlStatementHandler);
			}
		} catch (Exception e) {
			logger.error("create table时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			
			
		} finally {
			CloseUtil.closeDBConn(preparedStatement, statement, connection, rs);
		}
	}
	
	

	
	
	
	
	
	
	
	
	
	
	/**
	 * drop表
	 * @param dataSourceWrapper
	 * @param tables
	 */
	public void drop(DataSourceWrapper dataSourceWrapper, List<TableMetadata> tables) {
//		TableSqlStatementHandler tableSqlStatementHandler = DBRunEnvironmentContext.getDialect().getTableSqlStatementHandler();
//		
//		Connection connection = null;
//		Statement statement = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet rs = null;
//		try {
//			connection = dataSourceWrapper.getConnection(false).getConnection();
//			for (TableDrop tableDrop : tableDrops) {
//				
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			CloseUtil.closeDBConn(preparedStatement, statement, connection, rs);
//		}
	}
}
