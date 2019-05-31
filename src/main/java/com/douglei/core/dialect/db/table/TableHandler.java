package com.douglei.core.dialect.db.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
		logger.debug("执行的DDL SQL语句为: {}", ddlSQL);
		statement.execute(ddlSQL);
		statement.close();
	}
	
	/**
	 * 创建表
	 * @param table
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param list 
	 * @throws SQLException 
	 */
	private void createTable(TableMetadata table, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> list) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.tableCreateSqlStatement(table), connection, statement);
		if(list != null) {
			list.add(new DBObjectHolder(table, DBObjectType.TABLE, DBObjectOPType.CREATE));
		}
	}

	/**
	 * 删除表
	 * @param table
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param list 
	 * @throws SQLException 
	 */
	private void dropTable(TableMetadata table, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> list) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.tableDropSqlStatement(table.getName()), connection, statement);
		if(list != null) {
			list.add(new DBObjectHolder(table, DBObjectType.TABLE, DBObjectOPType.DROP));
		}
	}
	
	/**
	 * 创建约束
	 * @param constraint
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param list 
	 * @throws SQLException 
	 */
	private void createConstraint(Constraint constraint, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> list) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.constraintCreateSqlStatement(constraint), connection, statement);
		if(list != null) {
			list.add(new DBObjectHolder(constraint, DBObjectType.CONSTRAINT, DBObjectOPType.CREATE));
		}
	}
	
	/**
	 * 创建约束
	 * @param constraints
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param list 
	 * @throws SQLException 
	 */
	private void createConstraint(Collection<Constraint> constraints, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> list) throws SQLException {
		for (Constraint constraint : constraints) {
			createConstraint(constraint, connection, statement, tableSqlStatementHandler, list);
		}
	}
	
	/**
	 * 删除约束
	 * @param constraint
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param list 
	 * @throws SQLException 
	 */
	private void dropConstraint(Constraint constraint, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> list) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.constraintDropSqlStatement(constraint), connection, statement);
		if(list != null) {
			list.add(new DBObjectHolder(constraint, DBObjectType.CONSTRAINT, DBObjectOPType.DROP));
		}
	}
	
	/**
	 * 删除约束
	 * @param constraints
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param list 
	 * @throws SQLException 
	 */
	private void dropConstraint(Collection<Constraint> constraints, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> list) throws SQLException {
		for (Constraint constraint : constraints) {
			dropConstraint(constraint, connection, statement, tableSqlStatementHandler, list);
		}
	}
	
	/**
	 * 
	 * @param list
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @throws SQLException 
	 */
	private void rollback(List<DBObjectHolder> list, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler) throws SQLException {
		if(list.size() > 0) {
			logger.debug("开始回滚 DDL操作");
			DBObjectHolder holder = null;
			for(int i=list.size()-1;i>=0;i--) {
				holder = list.get(i);
				if(holder.getDbObjectType() == DBObjectType.TABLE) {
					if(holder.getDbObjectOPType() == DBObjectOPType.CREATE) {
						logger.debug("逆向: create ==> drop table");
						dropTable((TableMetadata)holder.getDbObject(), connection, statement, tableSqlStatementHandler, null);
					}else if(holder.getDbObjectOPType() == DBObjectOPType.DROP) {
						logger.debug("逆向: drop ==> create table");
						createTable((TableMetadata)holder.getDbObject(), connection, statement, tableSqlStatementHandler, null);
					}
				}else if(holder.getDbObjectType() == DBObjectType.CONSTRAINT) {
					if(holder.getDbObjectOPType() == DBObjectOPType.CREATE) {
						logger.debug("逆向: create ==> drop constraint");
						dropConstraint((Constraint)holder.getDbObject(), connection, statement, tableSqlStatementHandler, null);
					}else if(holder.getDbObjectOPType() == DBObjectOPType.DROP) {
						logger.debug("逆向: drop ==> create constraint");
						createConstraint((Constraint)holder.getDbObject(), connection, statement, tableSqlStatementHandler, null);
					}
				}else if(holder.getDbObjectType() == DBObjectType.INDEX) {
					throw new IllegalAccessError("目前还不支持处理索引");
				}
			}
		}
	}
	
	/**
	 * create表
	 * @param dataSourceWrapper
	 * @param tables
	 */
	public void create(DataSourceWrapper dataSourceWrapper, List<TableMetadata> tables) {
		TableSqlStatementHandler tableSqlStatementHandler = DBRunEnvironmentContext.getDialect().getTableSqlStatementHandler();
		List<DBObjectHolder> list = new ArrayList<DBObjectHolder>();
		
		Connection connection = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			connection = dataSourceWrapper.getConnection(false).getConnection();
			preparedStatement = connection.prepareStatement(tableSqlStatementHandler.tableExistsQueryPreparedSqlStatement());
			
			for (TableMetadata table : tables) {
				if(tableExists(table.getName(), preparedStatement, rs)) {
					if(table.getCreateMode() == CreateMode.CREATE) {
						continue;
					}
					logger.debug("正向: drop constraint");
					dropConstraint(table.getConstraints(), connection, statement, tableSqlStatementHandler, list);
					logger.debug("正向: drop table");
					dropTable(table, connection, statement, tableSqlStatementHandler, list);
				}
				logger.debug("正向: create table");
				createTable(table, connection, statement, tableSqlStatementHandler, list);
				logger.debug("正向: create constraint");
				createConstraint(table.getConstraints(), connection, statement, tableSqlStatementHandler, list);
			}
		} catch (Exception e) {
			logger.error("create 时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			try {
				rollback(list, connection, preparedStatement, tableSqlStatementHandler);
			} catch (SQLException e1) {
				logger.error("create 时出现异常后回滚, 回滚又出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
				e1.printStackTrace();
			}
		} finally {
			list.clear();
			list = null;
			CloseUtil.closeDBConn(rs, preparedStatement, statement, connection);
		}
	}
	
	/**
	 * drop表
	 * @param dataSourceWrapper
	 * @param tables
	 */
	public void drop(DataSourceWrapper dataSourceWrapper, List<TableMetadata> tables) {
		TableSqlStatementHandler tableSqlStatementHandler = DBRunEnvironmentContext.getDialect().getTableSqlStatementHandler();
		List<DBObjectHolder> list = new ArrayList<DBObjectHolder>();
		
		Connection connection = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			connection = dataSourceWrapper.getConnection(false).getConnection();
			preparedStatement = connection.prepareStatement(tableSqlStatementHandler.tableExistsQueryPreparedSqlStatement());
			
			for (TableMetadata table : tables) {
				if(tableExists(table.getName(), preparedStatement, rs)) {
					logger.debug("正向: drop constraint");
					dropConstraint(table.getConstraints(), connection, statement, tableSqlStatementHandler, list);
					logger.debug("正向: drop table");
					dropTable(table, connection, statement, tableSqlStatementHandler, list);
				}
			}
		} catch (Exception e) {
			logger.error("drop 时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			try {
				rollback(list, connection, preparedStatement, tableSqlStatementHandler);
			} catch (SQLException e1) {
				logger.error("drop 时出现异常后回滚, 回滚又出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
				e1.printStackTrace();
			}
		} finally {
			list.clear();
			list = null;
			CloseUtil.closeDBConn(rs, preparedStatement, statement, connection);
		}
	}
}
