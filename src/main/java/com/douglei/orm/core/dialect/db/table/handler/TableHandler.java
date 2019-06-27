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
import com.douglei.orm.core.dialect.db.table.entity.Column;
import com.douglei.orm.core.dialect.db.table.entity.Constraint;
import com.douglei.orm.core.dialect.db.table.entity.Index;
import com.douglei.orm.core.dialect.db.table.handler.dbobject.DBObjectHolder;
import com.douglei.orm.core.dialect.db.table.handler.dbobject.DBObjectOPType;
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
			dbObjectHolders.add(new DBObjectHolder(table, DBObjectType.TABLE, DBObjectOPType.CREATE));
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
			dbObjectHolders.add(new DBObjectHolder(table, DBObjectType.TABLE, DBObjectOPType.DROP));
		}
	}
	
	/**
	 * 修改表名
	 * @param originTableName
	 * @param targetTableName
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders
	 * @throws SQLException
	 */
	private void tableRename(String originTableName, String targetTableName, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.tableRenameSqlStatement(originTableName, targetTableName), connection, statement);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(originTableName, targetTableName, DBObjectType.TABLE, DBObjectOPType.RENAME));
		}
	}
	
	/**
	 * 创建列
	 * @param tableName
	 * @param column
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders
	 * @throws SQLException 
	 */
	private void createColumn(String tableName, Column column, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.columnCreateSqlStatement(tableName, column), connection, statement);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(tableName, column, DBObjectType.COLUMN, DBObjectOPType.CREATE));
		}
	}
	
	/**
	 * 删除列
	 * @param tableName
	 * @param column
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders
	 * @throws SQLException 
	 */
	private void dropColumn(String tableName, Column column, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.columnDropSqlStatement(tableName, column.getName()), connection, statement);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(tableName, column, DBObjectType.COLUMN, DBObjectOPType.DROP));
		}
	}
	
	/**
	 * 修改列名
	 * @param tableName
	 * @param originColumnName
	 * @param targetColumnName
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders
	 * @throws SQLException
	 */
	private void columnRename(String tableName, String originColumnName, String targetColumnName, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.columnRenameSqlStatement(tableName, originColumnName, targetColumnName), connection, statement);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(tableName, originColumnName, targetColumnName, DBObjectType.COLUMN, DBObjectOPType.RENAME));
		}
	}
	
	/**
	 * 修改列
	 * @param tableName
	 * @param originColumn
	 * @param targetColumn
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders
	 * @throws SQLException 
	 */
	private void columnModify(String tableName, Column originColumn, Column targetColumn, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.columnModifySqlStatement(tableName, targetColumn), connection, statement);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(tableName, originColumn, targetColumn, DBObjectType.COLUMN, DBObjectOPType.MODIFY));
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
			dbObjectHolders.add(new DBObjectHolder(constraint, DBObjectType.CONSTRAINT, DBObjectOPType.CREATE));
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
		if(constraints != null) {
			for (Constraint constraint : constraints) {
				createConstraint(constraint, connection, statement, tableSqlStatementHandler, dbObjectHolders);
			}
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
			dbObjectHolders.add(new DBObjectHolder(constraint, DBObjectType.CONSTRAINT, DBObjectOPType.DROP));
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
		if(constraints != null) {
			for (Constraint constraint : constraints) {
				dropConstraint(constraint, connection, statement, tableSqlStatementHandler, dbObjectHolders);
			}
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
			dbObjectHolders.add(new DBObjectHolder(index, DBObjectType.INDEX, DBObjectOPType.CREATE));
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
		if(indexes != null) {
			for (Index index : indexes) {
				createIndex(index, connection, statement, tableSqlStatementHandler, dbObjectHolders);
			}
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
			dbObjectHolders.add(new DBObjectHolder(index, DBObjectType.INDEX, DBObjectOPType.DROP));
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
		if(indexes != null) {
			for (Index index : indexes) {
				dropIndex(index, connection, statement, tableSqlStatementHandler, dbObjectHolders);
			}
		}
	}
	
	/**
	 * 回滚
	 * @param dbObjectHolders
	 * @param serializationObjectHolders
	 * @param connection
	 * @param statement
	 * @param tableSqlStatementHandler
	 * @throws SQLException 
	 */
	private void rollback(List<DBObjectHolder> dbObjectHolders, List<SerializationObjectHolder> serializationObjectHolders, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler) throws SQLException {
		rollbackDDL(dbObjectHolders, connection, statement, tableSqlStatementHandler);
		tableSerializationFileHandler.rollbackSerializationFile(serializationObjectHolders);
	}
	
	private void rollbackDDL(List<DBObjectHolder> dbObjectHolders, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler) throws SQLException {
		if(dbObjectHolders.size() > 0) {
			logger.debug("开始回滚 DDL操作");
			DBObjectHolder holder = null;
			for(int i=dbObjectHolders.size()-1;i>=0;i--) {
				holder = dbObjectHolders.get(i);
				switch(holder.getDbObjectType()) {
					case TABLE:
						if(holder.getDbObjectOPType() == DBObjectOPType.CREATE) {
							logger.debug("逆向: create ==> drop table");
							dropTable((TableMetadata)holder.getOriginObject(), connection, statement, tableSqlStatementHandler, null);
						}else if(holder.getDbObjectOPType() == DBObjectOPType.DROP) {
							logger.debug("逆向: drop ==> create table");
							createTable((TableMetadata)holder.getOriginObject(), connection, statement, tableSqlStatementHandler, null);
						}else if(holder.getDbObjectOPType() == DBObjectOPType.RENAME) {
							logger.debug("逆向: table rename");
							tableRename(holder.getTargetObject().toString(), holder.getOriginObject().toString(), connection, statement, tableSqlStatementHandler, null);
						}
						break;
					case COLUMN:
						if(holder.getDbObjectOPType() == DBObjectOPType.CREATE) {
							logger.debug("逆向: create ==> drop column");
							dropColumn(holder.getTableName(), (Column)holder.getOriginObject(), connection, statement, tableSqlStatementHandler, null);
						}else if(holder.getDbObjectOPType() == DBObjectOPType.DROP) {
							logger.debug("逆向: drop ==> create column");
							createColumn(holder.getTableName(), (Column)holder.getOriginObject(), connection, statement, tableSqlStatementHandler, null);
						}else if(holder.getDbObjectOPType() == DBObjectOPType.RENAME) {
							logger.debug("逆向: column rename");
							columnRename(holder.getTableName(), holder.getTargetObject().toString(), holder.getOriginObject().toString(), connection, statement, tableSqlStatementHandler, null);
						}else if(holder.getDbObjectOPType() == DBObjectOPType.MODIFY) {
							logger.debug("逆向: column modify");
							
							// 如果修改了列名, 则要将originColumn的列名暂时给targetColumn, 等修改完成后, 再替换回来, 因为在修改列的sql语句中, 会用到column.getName()
							String tmpColumnName = null;
							Column originColumn = (Column)holder.getTargetObject();
							Column targetColumn = (Column)holder.getOriginObject();
							boolean updateColumnName = !originColumn.getName().equals(targetColumn.getName());
							if(updateColumnName) {
								tmpColumnName = targetColumn.getName();
								targetColumn._danger2UpdateColumnName(originColumn.getName());
							}
							columnModify(holder.getTableName(), null, targetColumn, connection, statement, tableSqlStatementHandler, null);
							if(updateColumnName) {
								targetColumn._danger2UpdateColumnName(tmpColumnName);
							}
						}
						break;
					case CONSTRAINT:
						if(holder.getDbObjectOPType() == DBObjectOPType.CREATE) {
							logger.debug("逆向: create ==> drop constraint");
							dropConstraint((Constraint)holder.getOriginObject(), connection, statement, tableSqlStatementHandler, null);
						}else if(holder.getDbObjectOPType() == DBObjectOPType.DROP) {
							logger.debug("逆向: drop ==> create constraint");
							createConstraint((Constraint)holder.getOriginObject(), connection, statement, tableSqlStatementHandler, null);
						}
						break;
					case INDEX:
						if(holder.getDbObjectOPType() == DBObjectOPType.CREATE) {
							logger.debug("逆向: create ==> drop index");
							dropIndex((Index)holder.getOriginObject(), connection, statement, tableSqlStatementHandler, null);
						}else if(holder.getDbObjectOPType() == DBObjectOPType.DROP) {
							logger.debug("逆向: drop ==> create index");
							createIndex((Index)holder.getOriginObject(), connection, statement, tableSqlStatementHandler, null);
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
		TableSqlStatementHandler tableSqlStatementHandler = DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getTableSqlStatementHandler();
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
							syncTable(table, connection, statement, tableSqlStatementHandler, dbObjectHolders, serializationObjectHolders);
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
				logger.debug("正向: create serialization file");
				tableSerializationFileHandler.createSerializationFile(table, serializationObjectHolders);
			}
		} catch (Exception e) {
			logger.error("create 时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			try {
				rollback(dbObjectHolders, serializationObjectHolders, connection, preparedStatement, tableSqlStatementHandler);
			} catch (SQLException e1) {
				logger.error("create 时出现异常后回滚, 回滚又出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
				e1.printStackTrace();
			}
		} finally {
			dbObjectHolders.clear();
			dbObjectHolders = null;
			serializationObjectHolders.clear();
			serializationObjectHolders = null;
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
	 * @param serializationObjectHolders
	 * @throws SQLException 
	 */
	private void syncTable(TableMetadata table, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders, List<SerializationObjectHolder> serializationObjectHolders) throws SQLException {
		TableMetadata oldTable = tableSerializationFileHandler.deserializeFromFile(table);
		// 删除旧表的约束和索引
		dropConstraint(oldTable.getConstraints(), connection, statement, tableSqlStatementHandler, dbObjectHolders);
		dropIndex(oldTable.getIndexes(), connection, statement, tableSqlStatementHandler, dbObjectHolders);
		// 对表和列进行同步
		syncTable(table, oldTable, connection, statement, tableSqlStatementHandler, dbObjectHolders);
		syncColumns(table, oldTable, connection, statement, tableSqlStatementHandler, dbObjectHolders);
		// 创建新表的约束和索引
		createConstraint(table.getConstraints(), connection, statement, tableSqlStatementHandler, dbObjectHolders);
		createIndex(table.getIndexes(), connection, statement, tableSqlStatementHandler, dbObjectHolders);
		// 对序列化文件进行同步
		syncSerializationFile(table, oldTable, serializationObjectHolders);
	}
	
	// 同步表
	private void syncTable(TableMetadata table, TableMetadata oldTable, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		if(!table.getName().equals(oldTable.getName())) {
			logger.debug("正向: table rename");
			tableRename(oldTable.getName(), table.getName(), connection, statement, tableSqlStatementHandler, dbObjectHolders);
		}
	}
	// 同步列
	private void syncColumns(TableMetadata table, TableMetadata oldTable, Connection connection, Statement statement, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		Collection<Column> columns = table.getColumns();
		Column oldColumn = null;
		for (Column column : columns) {
			oldColumn = oldTable.getColumnByName(column.getOldName());
			if(oldColumn == null) {// 为空标识为新加的列
				logger.debug("正向: create column");
				createColumn(table.getName(), column, connection, statement, tableSqlStatementHandler, dbObjectHolders);
			}else {// 不为空, 标识可能为修改列
				if(!column.getName().equals(oldColumn.getName())) {
					logger.debug("正向: column rename");
					columnRename(table.getName(), oldColumn.getName(), column.getName(), connection, statement, tableSqlStatementHandler, dbObjectHolders);
				}
				if(isModifyColumn(table, column, oldColumn)) {
					logger.debug("正向: column modify");
					columnModify(table.getName(), oldColumn, column, connection, statement, tableSqlStatementHandler, dbObjectHolders);
				}
			}
		}
		
		columns = oldTable.getColumns();
		for (Column column : columns) {
			if(table.getColumnByName(column.getName()) == null) {
				dropColumn(table.getName(), column, connection, statement, tableSqlStatementHandler, dbObjectHolders);
			}
		}
	}
	// 是否修改列
	private boolean isModifyColumn(TableMetadata table, Column column, Column oldColumn) {
		if(DBRunEnvironmentContext.getEnvironmentProperty().getEnableColumnDynamicUpdateValidation()) {
			boolean isModifyColumn = false;
			if(column.getDataType() != oldColumn.getDataType()) {
				if(!DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getDBFeatures().supportColumnDataTypeConvert(oldColumn.getDataType(), column.getDataType())) {
					throw new ColumnModifyException("在数据库["+DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getType()+"]中, 修改["+table.getName()+"]表的["+column.getName()+"]列的数据类型时, 不支持从["+oldColumn.getDataType()+"]类型, 改为["+column.getDataType()+"]类型");
				}
				isModifyColumn = true;
			}
			if(column.getLength() != oldColumn.getLength()) {
				if(column.getLength() < oldColumn.getLength()) {
					throw new ColumnModifyException("修改["+table.getName()+"]表的["+column.getName()+"]列的数据长度值时, 新的长度值["+column.getLength()+"]不能小于原长度值["+oldColumn.getLength()+"]");
				}
				isModifyColumn = true;
			}
			if(column.getPrecision() != oldColumn.getPrecision()) {
				if(column.getPrecision() < oldColumn.getPrecision()) {
					throw new ColumnModifyException("修改["+table.getName()+"]表的["+column.getName()+"]列的数据精度值时, 新的精度值["+column.getPrecision()+"]不能小于原精度值["+oldColumn.getPrecision()+"]");
				}
				isModifyColumn = true;
			}
			if(column.isNullabled() != oldColumn.isNullabled()) {
				isModifyColumn = true;
			}
			return isModifyColumn;
		}
		return column.getDataType() != oldColumn.getDataType() || column.getLength() != oldColumn.getLength() || column.getPrecision() != oldColumn.getPrecision() || column.isNullabled() != oldColumn.isNullabled();
	}
	// 同步序列化文件
	private void syncSerializationFile(TableMetadata table, TableMetadata oldTable, List<SerializationObjectHolder> serializationObjectHolders) {
		tableSerializationFileHandler.updateSerializationFile(table, oldTable, serializationObjectHolders);
	}

	/**
	 * drop表
	 * @param dataSourceWrapper
	 * @param tables
	 */
	public void drop(DataSourceWrapper dataSourceWrapper, List<TableMetadata> tables) {
		TableSqlStatementHandler tableSqlStatementHandler = DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getTableSqlStatementHandler();
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
					logger.debug("正向: drop index");
					dropIndex(table.getIndexes(), connection, statement, tableSqlStatementHandler, dbObjectHolders);
					logger.debug("正向: drop constraint");
					dropConstraint(table.getConstraints(), connection, statement, tableSqlStatementHandler, dbObjectHolders);
					logger.debug("正向: drop table");
					dropTable(table, connection, statement, tableSqlStatementHandler, dbObjectHolders);
				}
				logger.debug("正向: drop serialization file");
				tableSerializationFileHandler.dropSerializationFile(table, serializationObjectHolders);
			}
		} catch (Exception e) {
			logger.error("drop 时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			try {
				rollback(dbObjectHolders, serializationObjectHolders, connection, preparedStatement, tableSqlStatementHandler);
			} catch (SQLException e1) {
				logger.error("drop 时出现异常后回滚, 回滚又出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
				e1.printStackTrace();
			}
		} finally {
			dbObjectHolders.clear();
			dbObjectHolders = null;
			serializationObjectHolders.clear();
			serializationObjectHolders = null;
			CloseUtil.closeDBConn(rs, preparedStatement, statement, connection);
		}
	}
}
