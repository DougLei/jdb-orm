package com.douglei.orm.core.dialect.db.table;

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
import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.store.MappingStore;
import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.core.dialect.db.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.core.dialect.db.table.dbobject.DBObjectHolder;
import com.douglei.orm.core.dialect.db.table.dbobject.DBObjectOPType;
import com.douglei.orm.core.dialect.db.table.dbobject.DBObjectType;
import com.douglei.orm.core.dialect.db.table.serializationobject.SerializeObjectHolder;
import com.douglei.orm.core.dialect.db.table.tablemapping.TableMappingHolder;
import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.Constraint;
import com.douglei.orm.core.metadata.table.Index;
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
	private boolean tableExists(String tableName, PreparedStatement preparedStatement) throws SQLException {
		ResultSet rs = null;
		try {
			preparedStatement.setString(1, tableName);
			rs = preparedStatement.executeQuery();
			if(rs.next()) {
				boolean tableExists = Integer.parseInt(rs.getObject(1).toString()) == 1;
				rs.close();
				return tableExists;
			}
			throw new NullPointerException("查询表是否存在时, ResultSet对象中没有任何数据");
		} finally {
			rs.close();
			rs = null;
		}
	}
	
	/**
	 * 执行DDL sql语句
	 * @param ddlSQL
	 * @param connection
	 * @throws SQLException
	 */
	private void executeDDLSQL(String ddlSQL, Connection connection) throws SQLException {
		logger.debug("执行的DDL SQL语句为: {}", ddlSQL);
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.execute(ddlSQL);
		} finally {
			statement.close();
			statement = null;
		}
	}
	
	/**
	 * 创建表
	 * @param table
	 * @param connection
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders 
	 * @throws SQLException 
	 */
	private void createTable(TableMetadata table, Connection connection, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.tableCreateSqlStatement(table), connection);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(table, DBObjectType.TABLE, DBObjectOPType.CREATE));
		}
	}

	/**
	 * 删除表
	 * @param table
	 * @param connection
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders 
	 * @throws SQLException 
	 */
	private void dropTable(TableMetadata table, Connection connection, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.tableDropSqlStatement(table.getName()), connection);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(table, DBObjectType.TABLE, DBObjectOPType.DROP));
		}
	}
	
	/**
	 * 修改表名
	 * @param originTableName
	 * @param targetTableName
	 * @param connection
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders
	 * @throws SQLException
	 */
	private void tableRename(String originTableName, String targetTableName, Connection connection, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.tableRenameSqlStatement(originTableName, targetTableName), connection);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(null, originTableName, targetTableName, DBObjectType.TABLE, DBObjectOPType.RENAME));
		}
	}
	
	/**
	 * 创建列
	 * @param tableName
	 * @param column
	 * @param connection
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders
	 * @throws SQLException 
	 */
	private void createColumn(String tableName, ColumnMetadata column, Connection connection, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.columnCreateSqlStatement(tableName, column), connection);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(tableName, column, DBObjectType.COLUMN, DBObjectOPType.CREATE));
		}
	}
	
	/**
	 * 删除列
	 * @param tableName
	 * @param column
	 * @param connection
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders
	 * @throws SQLException 
	 */
	private void dropColumn(String tableName, ColumnMetadata column, Connection connection, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.columnDropSqlStatement(tableName, column.getName()), connection);
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
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders
	 * @throws SQLException
	 */
	private void columnRename(String tableName, String originColumnName, String targetColumnName, Connection connection, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.columnRenameSqlStatement(tableName, originColumnName, targetColumnName), connection);
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
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders
	 * @throws SQLException 
	 */
	private void columnModify(String tableName, ColumnMetadata originColumn, ColumnMetadata targetColumn, Connection connection, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.columnModifySqlStatement(tableName, targetColumn), connection);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(tableName, originColumn, targetColumn, DBObjectType.COLUMN, DBObjectOPType.MODIFY));
		}
	}
	
	/**
	 * 创建约束
	 * @param constraint
	 * @param connection
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders 
	 * @throws SQLException 
	 */
	private void createConstraint(Constraint constraint, Connection connection, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.constraintCreateSqlStatement(constraint), connection);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(constraint, DBObjectType.CONSTRAINT, DBObjectOPType.CREATE));
		}
	}
	
	/**
	 * 创建约束
	 * @param constraints
	 * @param connection
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders 
	 * @throws SQLException 
	 */
	private void createConstraint(Collection<Constraint> constraints, Connection connection, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		if(constraints != null) {
			for (Constraint constraint : constraints) {
				createConstraint(constraint, connection, tableSqlStatementHandler, dbObjectHolders);
			}
		}
	}
	
	/**
	 * 删除约束
	 * @param constraint
	 * @param connection
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders 
	 * @throws SQLException 
	 */
	private void dropConstraint(Constraint constraint, Connection connection, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(tableSqlStatementHandler.constraintDropSqlStatement(constraint), connection);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(constraint, DBObjectType.CONSTRAINT, DBObjectOPType.DROP));
		}
	}
	
	/**
	 * 删除约束
	 * @param constraints
	 * @param connection
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders 
	 * @throws SQLException 
	 */
	private void dropConstraint(Collection<Constraint> constraints, Connection connection, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		if(constraints != null) {
			for (Constraint constraint : constraints) {
				dropConstraint(constraint, connection, tableSqlStatementHandler, dbObjectHolders);
			}
		}
	}
	
	/**
	 * 创建索引
	 * @param index
	 * @param connection
	 * @param dbObjectHolders
	 * @throws SQLException
	 */
	private void createIndex(Index index, Connection connection, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(index.getCreateSqlStatement(), connection);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(index, DBObjectType.INDEX, DBObjectOPType.CREATE));
		}
	}
	
	/**
	 * 创建索引
	 * @param indexes
	 * @param connection
	 * @param dbObjectHolders
	 * @throws SQLException
	 */
	private void createIndex(Collection<Index> indexes, Connection connection, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		if(indexes != null) {
			for (Index index : indexes) {
				createIndex(index, connection, dbObjectHolders);
			}
		}
	}
	
	/**
	 * 删除索引
	 * @param index
	 * @param connection
	 * @param dbObjectHolders
	 * @throws SQLException
	 */
	private void dropIndex(Index index, Connection connection, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		executeDDLSQL(index.getDropSqlStatement(), connection);
		if(dbObjectHolders != null) {
			dbObjectHolders.add(new DBObjectHolder(index, DBObjectType.INDEX, DBObjectOPType.DROP));
		}
	}
	
	/**
	 * 删除索引
	 * @param indexes
	 * @param connection
	 * @param dbObjectHolders
	 * @throws SQLException
	 */
	private void dropIndex(Collection<Index> indexes, Connection connection, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		if(indexes != null) {
			for (Index index : indexes) {
				dropIndex(index, connection, dbObjectHolders);
			}
		}
	}
	
	
	/**
	 * 创建主键序列
	 * @param primaryKeySequence
	 * @param connection
	 * @param dbObjectHolders 
	 * @throws SQLException 
	 */
	private void createPrimaryKeySequence(PrimaryKeySequence primaryKeySequence, Connection connection, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		if(primaryKeySequence != null && primaryKeySequence.use()) {
			executeDDLSQL(primaryKeySequence.getCreateSql(), connection);
			if(dbObjectHolders != null) {
				dbObjectHolders.add(new DBObjectHolder(primaryKeySequence, DBObjectType.PRIMARY_KEY_SEQUENCE, DBObjectOPType.CREATE));
			}
		}
	}

	/**
	 * 删除主键序列
	 * @param primaryKeySequence
	 * @param connection
	 * @param dbObjectHolders 
	 * @throws SQLException 
	 */
	private void dropPrimaryKeySequence(PrimaryKeySequence primaryKeySequence, Connection connection, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		if(primaryKeySequence != null && primaryKeySequence.use()) {
			executeDDLSQL(primaryKeySequence.getDropSql(), connection);
			if(dbObjectHolders != null) {
				dbObjectHolders.add(new DBObjectHolder(primaryKeySequence, DBObjectType.PRIMARY_KEY_SEQUENCE, DBObjectOPType.DROP));
			}
		}
	}
	
	
	
	/**
	 * 回滚
	 * @param tableMappingHolder
	 * @param dbObjectHolders
	 * @param serializeObjectHolders
	 * @param connection
	 * @param tableSqlStatementHandler
	 * @throws RollbackException 
	 */
	private void rollback(TableMappingHolder tableMappingHolder, List<DBObjectHolder> dbObjectHolders, List<SerializeObjectHolder> serializeObjectHolders, Connection connection, TableSqlStatementHandler tableSqlStatementHandler) throws RollbackException {
		try {
			rollbackTableMapping(tableMappingHolder);
			rollbackDDL(dbObjectHolders, connection, tableSqlStatementHandler);
			tableSerializationFileHandler.rollbackSerializationFile(serializeObjectHolders);
		} catch (SQLException e) {
			throw new RollbackException("回滚表操作时出现异常", e);
		}
	}
	
	private void rollbackTableMapping(TableMappingHolder tableMappingHolder) {
		MappingStore mcs = DBRunEnvironmentContext.getEnvironmentProperty().getMappingStore();
		switch(tableMappingHolder.getTableMappingOPType()) {
			case CREATE:
				mcs.addMapping(tableMappingHolder.getTableMappings());
				break;
			case DROP:
				mcs.removeMapping(tableMappingHolder.getTableMappingCodes());
				break;
		}
	}

	/**
	 * 
	 * @param dbObjectHolders
	 * @param connection
	 * @param tableSqlStatementHandler
	 * @throws SQLException
	 */
	private void rollbackDDL(List<DBObjectHolder> dbObjectHolders, Connection connection, TableSqlStatementHandler tableSqlStatementHandler) throws SQLException {
		if(dbObjectHolders.size() > 0) {
			logger.debug("开始回滚 DDL操作");
			DBObjectHolder holder = null;
			for(int i=dbObjectHolders.size()-1;i>=0;i--) {
				holder = dbObjectHolders.get(i);
				switch(holder.getDbObjectType()) {
					case TABLE:
						if(holder.getDbObjectOPType() == DBObjectOPType.CREATE) {
							logger.debug("逆向: create ==> drop table");
							dropTable((TableMetadata)holder.getOriginObject(), connection, tableSqlStatementHandler, null);
						}else if(holder.getDbObjectOPType() == DBObjectOPType.DROP) {
							logger.debug("逆向: drop ==> create table");
							createTable((TableMetadata)holder.getOriginObject(), connection, tableSqlStatementHandler, null);
						}else if(holder.getDbObjectOPType() == DBObjectOPType.RENAME) {
							logger.debug("逆向: table rename");
							tableRename(holder.getTargetObject().toString(), holder.getOriginObject().toString(), connection, tableSqlStatementHandler, null);
						}
						break;
					case COLUMN:
						if(holder.getDbObjectOPType() == DBObjectOPType.CREATE) {
							logger.debug("逆向: create ==> drop column");
							dropColumn(holder.getTableName(), (ColumnMetadata)holder.getOriginObject(), connection, tableSqlStatementHandler, null);
						}else if(holder.getDbObjectOPType() == DBObjectOPType.DROP) {
							logger.debug("逆向: drop ==> create column");
							createColumn(holder.getTableName(), (ColumnMetadata)holder.getOriginObject(), connection, tableSqlStatementHandler, null);
						}else if(holder.getDbObjectOPType() == DBObjectOPType.RENAME) {
							logger.debug("逆向: column rename");
							columnRename(holder.getTableName(), holder.getTargetObject().toString(), holder.getOriginObject().toString(), connection, tableSqlStatementHandler, null);
						}else if(holder.getDbObjectOPType() == DBObjectOPType.MODIFY) {
							logger.debug("逆向: column modify");
							
							// 如果修改了列名, 则要将originColumn的列名暂时给targetColumn, 等修改完成后, 再替换回来, 因为在修改列的sql语句中, 会用到column.getName()
							String tmpColumnName = null;
							ColumnMetadata originColumn = (ColumnMetadata)holder.getTargetObject();
							ColumnMetadata targetColumn = (ColumnMetadata)holder.getOriginObject();
							boolean updateColumnName = !originColumn.getName().equals(targetColumn.getName());
							if(updateColumnName) {
								tmpColumnName = targetColumn.getName();
								targetColumn.forceUpdateName(originColumn.getName());
							}
							columnModify(holder.getTableName(), null, targetColumn, connection, tableSqlStatementHandler, null);
							if(updateColumnName) {
								targetColumn.forceUpdateName(tmpColumnName);
							}
						}
						break;
					case CONSTRAINT:
						if(holder.getDbObjectOPType() == DBObjectOPType.CREATE) {
							logger.debug("逆向: create ==> drop constraint");
							dropConstraint((Constraint)holder.getOriginObject(), connection, tableSqlStatementHandler, null);
						}else if(holder.getDbObjectOPType() == DBObjectOPType.DROP) {
							logger.debug("逆向: drop ==> create constraint");
							createConstraint((Constraint)holder.getOriginObject(), connection, tableSqlStatementHandler, null);
						}
						break;
					case INDEX:
						if(holder.getDbObjectOPType() == DBObjectOPType.CREATE) {
							logger.debug("逆向: create ==> drop index");
							dropIndex((Index)holder.getOriginObject(), connection, null);
						}else if(holder.getDbObjectOPType() == DBObjectOPType.DROP) {
							logger.debug("逆向: drop ==> create index");
							createIndex((Index)holder.getOriginObject(), connection, null);
						}
						break;
					case PRIMARY_KEY_SEQUENCE:
						if(holder.getDbObjectOPType() == DBObjectOPType.CREATE) {
							logger.debug("逆向: create ==> drop primary key sequence");
							dropPrimaryKeySequence((PrimaryKeySequence)holder.getOriginObject(), connection, null);
						}else if(holder.getDbObjectOPType() == DBObjectOPType.DROP) {
							logger.debug("逆向: drop ==> create primary key sequence");
							createPrimaryKeySequence((PrimaryKeySequence)holder.getOriginObject(), connection, null);
						}
				}
			}
		}
	}

	/**
	 * create表
	 * @param dataSourceWrapper
	 * @param tableMappings
	 */
	public void create(DataSourceWrapper dataSourceWrapper, List<Mapping> tableMappings) {
		TableSqlStatementHandler tableSqlStatementHandler = DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getTableSqlStatementHandler();
		List<String> tableMappingCodes = new ArrayList<String>(tableMappings.size());
		List<DBObjectHolder> dbObjectHolders = new ArrayList<DBObjectHolder>(tableMappings.size()*2);
		List<SerializeObjectHolder> serializeObjectHolders = new ArrayList<SerializeObjectHolder>(tableMappings.size());
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSourceWrapper.getConnection(false).getConnection();
			preparedStatement = connection.prepareStatement(tableSqlStatementHandler.tableExistsQueryPreparedSqlStatement());
			
			TableMetadata table = null;
			for (Mapping tableMapping : tableMappings) {
				tableMappingCodes.add(tableMapping.getCode());
				table = (TableMetadata) tableMapping.getMetadata();
				if(tableExists(table.getOldName(), preparedStatement)) {
					switch(table.getCreateMode()) {
						case DROP_CREATE:
							logger.debug("正向: drop primary key sequence");
							dropPrimaryKeySequence(table.getPrimaryKeySequence(), connection, dbObjectHolders);
							logger.debug("正向: drop index");
							dropIndex(table.getIndexes(), connection, dbObjectHolders);
							logger.debug("正向: drop constraint");
							dropConstraint(table.getConstraints(), connection, tableSqlStatementHandler, dbObjectHolders);
							logger.debug("正向: drop table");
							dropTable(table, connection, tableSqlStatementHandler, dbObjectHolders);
							break;
						case DYNAMIC_UPDATE:
							syncTable(table, connection, tableSqlStatementHandler, dbObjectHolders, serializeObjectHolders);
							continue;
						default:
							logger.debug("createTable, 而table存在时, 不处理table.createMode={}的表数据", table.getCreateMode());
							continue;
					}
				}
				logger.debug("正向: create table");
				createTable(table, connection, tableSqlStatementHandler, dbObjectHolders);
				logger.debug("正向: create constraint");
				createConstraint(table.getConstraints(), connection, tableSqlStatementHandler, dbObjectHolders);
				logger.debug("正向: create index");
				createIndex(table.getIndexes(), connection, dbObjectHolders);
				logger.debug("正向: create primary key sequence");
				createPrimaryKeySequence(table.getPrimaryKeySequence(), connection, dbObjectHolders);
				logger.debug("正向: create serialization file");
				tableSerializationFileHandler.createSerializationFile(table, serializeObjectHolders);
			}
		} catch (Exception e) {
			logger.error("create表时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			try {
				rollback(new TableMappingHolder(null, tableMappingCodes), dbObjectHolders, serializeObjectHolders, connection, tableSqlStatementHandler);
			} catch (RollbackException e1) {
				logger.error("create表时出现异常后回滚, 回滚又出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
				throw e1;
			}
			throw new CreateTableException("创建表时出现异常", e);
		} finally {
			clear(tableMappings, tableMappingCodes, dbObjectHolders, serializeObjectHolders);
			CloseUtil.closeDBConn(preparedStatement, connection);
		}
	}
	
	/**
	 * 同步表
	 * @param table
	 * @param connection
	 * @param tableSqlStatementHandler
	 * @param dbObjectHolders
	 * @param serializeObjectHolders
	 * @throws SQLException 
	 */
	private void syncTable(TableMetadata table, Connection connection, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders, List<SerializeObjectHolder> serializeObjectHolders) throws SQLException {
		TableMetadata oldTable = tableSerializationFileHandler.deserializeFromFile(table);
		if(isUpdateTable(table, oldTable)) {
			// 删除旧表的约束和索引
			dropConstraint(oldTable.getConstraints(), connection, tableSqlStatementHandler, dbObjectHolders);
			dropIndex(oldTable.getIndexes(), connection, dbObjectHolders);
			// 对表和列进行同步
			syncTable(table, oldTable, connection, tableSqlStatementHandler, dbObjectHolders);
			syncColumns(table, oldTable, connection, tableSqlStatementHandler, dbObjectHolders);
			// 创建新表的约束和索引
			createConstraint(table.getConstraints(), connection, tableSqlStatementHandler, dbObjectHolders);
			createIndex(table.getIndexes(), connection, dbObjectHolders);
			// 对序列化文件进行同步
			syncSerializationFile(table, oldTable, serializeObjectHolders);
		}else {
			logger.debug("[{}]表没有任何更新, 不执行同步操作", table.getName());
		}
	}
	
	// 是否更新了表
	private boolean isUpdateTable(TableMetadata table, TableMetadata oldTable) {
		if(!table.getName().equals(oldTable.getName()) 
				|| isUpdateColumn(table.getDeclareColumns(), oldTable)
				|| isUpdateConstraint(table.getConstraints(), oldTable)
				|| isUpdateIndex(table.getIndexes(), oldTable)) {
			return true;
		}
		return false;
	}
	// 是否更新了列
	private boolean isUpdateColumn(Collection<ColumnMetadata> columns, TableMetadata oldTable) {
		if(columns.size() != oldTable.getDeclareColumns().size()) {
			return true;
		}
		ColumnMetadata oldColumn = null;
		for (ColumnMetadata column : columns) {
			oldColumn = oldTable.getColumnByName(column.getOldName(), false);
			if(oldColumn == null) {
				return true;
			}else {// 不为空, 标识可能为修改列
				if(!column.getName().equals(oldColumn.getName())) {
					return true;
				}
				if(isModifyColumn_simpleValidate(column, oldColumn)) {
					return true;
				}
			}
		}
		return false;
	}
	// 是否更新了约束
	private boolean isUpdateConstraint(Collection<Constraint> constraints, TableMetadata oldTable) {
		Collection<Constraint> oldConstraints = oldTable.getConstraints();
		if(constraints == null) {
			if(oldConstraints != null) {
				return true;
			}
		}else {
			if(oldConstraints == null || constraints.size() != oldConstraints.size()) {
				return true;
			}
			for (Constraint constraint : constraints) {
				if(oldTable.getConstraintByName(constraint.getName()) == null) {
					return true;
				}
			}
		}
		return false;
	}
	// 是否更新了索引
	private boolean isUpdateIndex(Collection<Index> indexes, TableMetadata oldTable) {
		Collection<Index> oldIndexes = oldTable.getIndexes();
		if(indexes == null) {
			if(oldIndexes != null) {
				return true;
			}
		}else {
			if(oldIndexes == null || indexes.size() != oldIndexes.size()) {
				return true;
			}
			for (Index index : indexes) {
				if(oldTable.getIndexByName(index.getName()) == null) {
					return true;
				}
			}
		}
		return false;
	}
	// 同步表
	private void syncTable(TableMetadata table, TableMetadata oldTable, Connection connection, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		if(!table.getName().equals(oldTable.getName())) {
			logger.debug("正向: table rename");
			tableRename(oldTable.getName(), table.getName(), connection, tableSqlStatementHandler, dbObjectHolders);
		}
	}
	// 同步列
	private void syncColumns(TableMetadata table, TableMetadata oldTable, Connection connection, TableSqlStatementHandler tableSqlStatementHandler, List<DBObjectHolder> dbObjectHolders) throws SQLException {
		Collection<ColumnMetadata> columns = table.getDeclareColumns();
		ColumnMetadata oldColumn = null;
		for (ColumnMetadata column : columns) {
			oldColumn = oldTable.getColumnByName(column.getOldName(), false);
			if(oldColumn == null) {// 为空标识为新加的列
				logger.debug("正向: create column");
				createColumn(table.getName(), column, connection, tableSqlStatementHandler, dbObjectHolders);
			}else {// 不为空, 标识可能为修改列
				if(!column.getName().equals(oldColumn.getName())) {
					logger.debug("正向: column rename");
					columnRename(table.getName(), oldColumn.getName(), column.getName(), connection, tableSqlStatementHandler, dbObjectHolders);
				}
				if(isModifyColumn(table, column, oldColumn)) {
					logger.debug("正向: column modify");
					columnModify(table.getName(), oldColumn, column, connection, tableSqlStatementHandler, dbObjectHolders);
				}
			}
		}
		
		Collection<ColumnMetadata> oldColumns = oldTable.getDeclareColumns();
		for (ColumnMetadata oldColumn_ : oldColumns) {
			if(table.getColumnByName(oldColumn_.getName(), false) == null && getColumnByOldName(oldColumn_.getName(), columns) == null) {
				dropColumn(table.getName(), oldColumn_, connection, tableSqlStatementHandler, dbObjectHolders);
			}
		}
	}
	// 是否修改列
	private boolean isModifyColumn(TableMetadata table, ColumnMetadata column, ColumnMetadata oldColumn) {
		if(DBRunEnvironmentContext.getEnvironmentProperty().enableColumnDynamicUpdateValidate()) {
			boolean isModifyColumn = false;
			if(column.getDataTypeHandler().unEquals(oldColumn.getDataTypeHandler())) {
				if(!DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getDBFeatures().supportColumnDataTypeConvert(oldColumn.getDBDataType(), column.getDBDataType())) {
					throw new ColumnModifyException("在数据库["+DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getType()+"]中, 修改["+table.getName()+"]表的["+column.getName()+"]列的数据类型时, 不支持从["+oldColumn.getDBDataType()+"]类型, 改为["+column.getDBDataType()+"]类型");
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
			if(column.isNullable() != oldColumn.isNullable()) {
				isModifyColumn = true;
			}
			return isModifyColumn;
		}
		return isModifyColumn_simpleValidate(column, oldColumn);
	}
	// 是否修改列(简单判断)
	private boolean isModifyColumn_simpleValidate(ColumnMetadata column, ColumnMetadata oldColumn) {
		return column.getDataTypeHandler().unEquals(oldColumn.getDataTypeHandler()) || column.getLength() != oldColumn.getLength() || column.getPrecision() != oldColumn.getPrecision() || column.isNullable() != oldColumn.isNullable();
	}
	// 根据列的oldName, 查询列对象
	private ColumnMetadata getColumnByOldName(String oldColumnName, Collection<ColumnMetadata> columns) {
		for (ColumnMetadata column : columns) {
			if(column.getOldName().equals(oldColumnName)) {
				return column;
			}
		}
		return null;
	}
	// 同步序列化文件
	private void syncSerializationFile(TableMetadata table, TableMetadata oldTable, List<SerializeObjectHolder> serializeObjectHolders) {
		tableSerializationFileHandler.updateSerializationFile(table, oldTable, serializeObjectHolders);
	}

	/**
	 * drop表
	 * @param dataSourceWrapper
	 * @param tableMappings
	 */
	public void drop(DataSourceWrapper dataSourceWrapper, List<Mapping> tableMappings) {
		TableSqlStatementHandler tableSqlStatementHandler = DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getTableSqlStatementHandler();
		List<DBObjectHolder> dbObjectHolders = new ArrayList<DBObjectHolder>(tableMappings.size()*2);
		List<SerializeObjectHolder> serializeObjectHolders = new ArrayList<SerializeObjectHolder>(tableMappings.size());
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = dataSourceWrapper.getConnection(false).getConnection();
			preparedStatement = connection.prepareStatement(tableSqlStatementHandler.tableExistsQueryPreparedSqlStatement());
			
			TableMetadata table = null;
			for (Mapping tableMapping : tableMappings) {
				table = (TableMetadata) tableMapping.getMetadata();
				if(tableExists(table.getOldName(), preparedStatement)) {
					logger.debug("正向: drop primary key sequence");
					dropPrimaryKeySequence(table.getPrimaryKeySequence(), connection, dbObjectHolders);
					logger.debug("正向: drop index");
					dropIndex(table.getIndexes(), connection, dbObjectHolders);
					logger.debug("正向: drop constraint");
					dropConstraint(table.getConstraints(), connection, tableSqlStatementHandler, dbObjectHolders);
					logger.debug("正向: drop table");
					dropTable(table, connection, tableSqlStatementHandler, dbObjectHolders);
				}
				logger.debug("正向: drop serialization file");
				tableSerializationFileHandler.dropSerializationFile(table, serializeObjectHolders);
			}
		} catch (Exception e) {
			logger.error("drop表时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			try {
				rollback(new TableMappingHolder(tableMappings, null), dbObjectHolders, serializeObjectHolders, connection, tableSqlStatementHandler);
			} catch (RollbackException e1) {
				logger.error("drop表时出现异常后回滚, 回滚又出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
				throw e1;
			}
			throw new DropTableException("删除表时出现异常", e);
		} finally {
			clear(tableMappings, null, dbObjectHolders, serializeObjectHolders);
			CloseUtil.closeDBConn(preparedStatement, connection);
		}
	}
	
	/**
	 * 
	 * @param tableMappings
	 * @param tableMappingCodes
	 * @param dbObjectHolders
	 * @param serializeObjectHolders
	 */
	private void clear(List<Mapping> tableMappings, List<String> tableMappingCodes, List<DBObjectHolder> dbObjectHolders, List<SerializeObjectHolder> serializeObjectHolders) {
		tableMappings.clear();
		if(tableMappingCodes != null) {
			tableMappingCodes.clear();
		}
		dbObjectHolders.clear();
		serializeObjectHolders.clear();
	}
}
