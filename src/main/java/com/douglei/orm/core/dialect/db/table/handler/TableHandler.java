package com.douglei.orm.core.dialect.db.table.handler;

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

import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.core.dialect.db.table.entity.Constraint;
import com.douglei.orm.core.dialect.db.table.entity.Index;
import com.douglei.orm.core.dialect.db.table.handler.dbobject.DBObjectHolder;
import com.douglei.orm.core.dialect.db.table.handler.dbobject.DBObjectType;
import com.douglei.orm.core.dialect.db.table.handler.serializationobject.SerializationObjectHolder;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.tools.utils.CloseUtil;
import com.douglei.tools.utils.ExceptionUtil;


/**
 * 
 * @author DougLei
 */
public class TableHandler {
	private static final Logger logger = LoggerFactory.getLogger(TableHandler.class);
	private static final TableSerializationFileHandler tableSerializationFileHandler = new TableSerializationFileHandler();
	
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
	 * @param dbObjectHolders 
	 * @throws SQLException 
	 */
	private void createTable(TableMetadata table, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.tableCreateSqlStatement(table), connection, statement);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(table, DBObjectType.TABLE, OPType.CREATE));
		}
	}

	/**
	 * 删除表
	 * @param table
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders 
	 * @throws SQLException 
	 */
	private void dropTable(TableMetadata table, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.tableDropSqlStatement(table.getName()), connection, statement);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(table, DBObjectType.TABLE, OPType.DROP));
		}
	}
	
	/**
	 * 创建约束
	 * @param constraint
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders 
	 * @throws SQLException 
	 */
	private void createConstraint(Constraint constraint, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.constraintCreateSqlStatement(constraint), connection, statement);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(constraint, DBObjectType.CONSTRAINT, OPType.CREATE));
		}
	}
	
	/**
	 * 创建约束
	 * @param constraints
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders 
	 * @throws SQLException 
	 */
	private void createConstraint(Collection<Constraint> constraints, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		for (Constraint constraint : constraints) {
			createConstraint(constraint, connection, statement, tableSqlStatementHandler, dbObjectHolders);
		}
	}
	
	/**
	 * 删除约束
	 * @param constraint
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders 
	 * @throws SQLException 
	 */
	private void dropConstraint(Constraint constraint, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.constraintDropSqlStatement(constraint), connection, statement);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(constraint, DBObjectType.CONSTRAINT, OPType.DROP));
		}
	}
	
	/**
	 * 删除约束
	 * @param constraints
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders 
	 * @throws SQLException 
	 */
	private void dropConstraint(Collection<Constraint> constraints, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		for (Constraint constraint : constraints) {
			dropConstraint(constraint, connection, statement, tableSqlStatementHandler, dbObjectHolders);
		}
	}
	
	/**
	 * 创建索引
	 * @param index
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders
	 * @throws SQLException
	 */
	private void createIndex(Index index, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(index.getCreateSqlStatement(), connection, statement);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(index, DBObjectType.INDEX, OPType.CREATE));
		}
	}
	
	/**
	 * 创建索引
	 * @param indexes
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders
	 * @throws SQLException
	 */
	private void createIndex(Collection<Index> indexes, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		for (Index index : indexes) {
			createIndex(index, connection, statement, tableSqlStatementHandler, dbObjectHolders);
		}
	}
	
	/**
	 * 删除索引
	 * @param index
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders
	 * @throws SQLException
	 */
	private void dropIndex(Index index, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(index.getDropSqlStatement(), connection, statement);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(index, DBObjectType.INDEX, OPType.DROP));
		}
	}
	
	/**
	 * 删除索引
	 * @param indexes
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders
	 * @throws SQLException
	 */
	private void dropIndex(Collection<Index> indexes, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		for (Index index : indexes) {
			dropIndex(index, connection, statement, tableSqlStatementHandler, dbObjectHolders);
		}
	}
	
	/**
	 * 回滚
	 * @param dbObjectHolders
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @throws SQLException 
	 */
	private void rollback(List<DBObjectHolder> dbObjectHolders, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler) throws SQLException {
		if(dbObjectHolders.size() > 0) {
			logger.debug("开始回滚 DDL操作");
			DBObjectHolder holder = null;
			for(int i=dbObjectHolders.size()-1;i>=0;i--) {
				holder = dbObjectHolders.get(i);
				switch(holder.getDbObjectType()) {
					case TABLE:
						if(holder.getDbObjectOPType() == OPType.CREATE) {
							logger.debug("逆向: create ==> drop table");
							dropTable((TableMetadata)holder.getDbObject(), connection, statement, tableSqlStatementHandler, null);
						}else if(holder.getDbObjectOPType() == OPType.DROP) {
							logger.debug("逆向: drop ==> create table");
							createTable((TableMetadata)holder.getDbObject(), connection, statement, tableSqlStatementHandler, null);
						}
						break;
					case CONSTRAINT:
						if(holder.getDbObjectOPType() == OPType.CREATE) {
							logger.debug("逆向: create ==> drop constraint");
							dropConstraint((Constraint)holder.getDbObject(), connection, statement, tableSqlStatementHandler, null);
						}else if(holder.getDbObjectOPType() == OPType.DROP) {
							logger.debug("逆向: drop ==> create constraint");
							createConstraint((Constraint)holder.getDbObject(), connection, statement, tableSqlStatementHandler, null);
						}
						break;
					case INDEX:
						if(holder.getDbObjectOPType() == OPType.CREATE) {
							logger.debug("逆向: create ==> drop index");
							dropIndex((Index)holder.getDbObject(), connection, statement, tableSqlStatementHandler, null);
						}else if(holder.getDbObjectOPType() == OPType.DROP) {
							logger.debug("逆向: drop ==> create index");
							createIndex((Index)holder.getDbObject(), connection, statement, tableSqlStatementHandler, null);
						}
						break;
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
		List<DBObjectHolder> dbObjectHolders = new ArrayList<DBObjectHolder>(tables.size());
		List<SerializationObjectHolder> serializationObjectHolders = new ArrayList<SerializationObjectHolder>(tables.size());
		
		Connection connection = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			connection = dataSourceWrapper.getConnection(false).getConnection();
			preparedStatement = connection.prepareStatement(tableSqlStatementHandler.tableExistsQueryPreparedSqlStatement());
			
			for (TableMetadata table : tables) {
				if(tableExists(table.getName(), preparedStatement, rs)) {
					switch(table.getCreateMode()) {
						case DROP_CREATE:
							logger.debug("正向: drop index");
							dropIndex(table.getIndexes(), connection, statement, tableSqlStatementHandler, dbObjectHolders);
							logger.debug("正向: drop constraint");
							dropConstraint(table.getConstraints(), connection, statement, tableSqlStatementHandler, dbObjectHolders);
							logger.debug("正向: drop table");
							dropTable(table, connection, statement, tableSqlStatementHandler, dbObjectHolders);
							break;
						case DYNAMIC_UPDATE:
							syncTable(table, connection, statement, tableSqlStatementHandler, dbObjectHolders);
							continue;
						default:
							logger.debug("createTable, 而table存在时, 不处理table.createMode={}的表数据", table.getCreateMode());
							continue;
					}
				}
				logger.debug("正向: create table");
				createTable(table, connection, statement, tableSqlStatementHandler, dbObjectHolders);
				logger.debug("正向: create constraint");
				createConstraint(table.getConstraints(), connection, statement, tableSqlStatementHandler, dbObjectHolders);
				logger.debug("正向: create index");
				createIndex(table.getIndexes(), connection, statement, tableSqlStatementHandler, dbObjectHolders);
				
				// TODO 先做！！！将当前TableMetadata序列化到磁盘中: 当前系统所在的目录中: orm-serialization-files/sessionFactoryId/表名.tm
			}
		} catch (Exception e) {
			logger.error("create 时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			try {
				rollback(dbObjectHolders, connection, preparedStatement, tableSqlStatementHandler);
			} catch (SQLException e1) {
				logger.error("create 时出现异常后回滚, 回滚又出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
				e1.printStackTrace();
			}
		} finally {
			dbObjectHolders.clear();
			dbObjectHolders = null;
			CloseUtil.closeDBConn(rs, preparedStatement, statement, connection);
		}
	}
	
	/**
	 * 同步表
	 * @param table
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders
	 */
	private void syncTable(TableMetadata table, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) {
		// TODO 先做！！！获取之前保存的映射对象信息, 并和现在的比对, 进行表同步
		
		
		throw new IllegalArgumentException("table.createMode=DYNAMIC_UPDATE 同步表的方法还没做呢");
	}

	/**
	 * drop表
	 * @param dataSourceWrapper
	 * @param tables
	 */
	public void drop(DataSourceWrapper dataSourceWrapper, List<TableMetadata> tables) {
		TableSqlStatementHandler tableSqlStatementHandler = DBRunEnvironmentContext.getDialect().getTableSqlStatementHandler();
		List<DBObjectHolder> dbObjectHolders = new ArrayList<DBObjectHolder>();
		
		Connection connection = null;
		Statement statement = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			connection = dataSourceWrapper.getConnection(false).getConnection();
			preparedStatement = connection.prepareStatement(tableSqlStatementHandler.tableExistsQueryPreparedSqlStatement());
			
			for (TableMetadata table : tables) {
				if(tableExists(table.getName(), preparedStatement, rs)) {
					logger.debug("正向: drop index");
					dropIndex(table.getIndexes(), connection, statement, tableSqlStatementHandler, dbObjectHolders);
					logger.debug("正向: drop constraint");
					dropConstraint(table.getConstraints(), connection, statement, tableSqlStatementHandler, dbObjectHolders);
					logger.debug("正向: drop table");
					dropTable(table, connection, statement, tableSqlStatementHandler, dbObjectHolders);
				}
				
				// TODO 先做！！！尝试删除对应的序列化文件
				// TODO 先做！！！ 可能还有对这些序列化文件的正向/逆向操作，思考一下
			}
		} catch (Exception e) {
			logger.error("drop 时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			try {
				rollback(dbObjectHolders, connection, preparedStatement, tableSqlStatementHandler);
			} catch (SQLException e1) {
				logger.error("drop 时出现异常后回滚, 回滚又出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
				e1.printStackTrace();
			}
		} finally {
			dbObjectHolders.clear();
			dbObjectHolders = null;
			CloseUtil.closeDBConn(rs, preparedStatement, statement, connection);
		}
	}
}
