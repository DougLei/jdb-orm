package com.douglei.orm.core.dialect.mapping;

import java.sql.SQLException;
import java.util.Collection;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.core.dialect.db.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.core.dialect.db.table.TableSqlStatementHandler;
import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.Constraint;
import com.douglei.orm.core.metadata.table.CreateMode;
import com.douglei.orm.core.metadata.table.Index;
import com.douglei.orm.core.metadata.table.TableMetadata;

/**
 * 表结构处理器
 * @author DougLei
 */
class TableStructHandler {
	private static final TableSerializationHandler tableSerializationHandler = new TableSerializationHandler();
	private static final ThreadLocal<TableStructConnection> tableStructConnection = new ThreadLocal<TableStructConnection>();
	private DataSourceWrapper dataSourceWrapper;
	private TableSqlStatementHandler tableSqlStatementHandler;
	
	public TableStructHandler(DataSourceWrapper dataSourceWrapper, TableSqlStatementHandler tableSqlStatementHandler) {
		this.dataSourceWrapper = dataSourceWrapper;
		this.tableSqlStatementHandler = tableSqlStatementHandler;
	}

	// 获取操作表结构的数据库连接
	private TableStructConnection getTableStructConnection() throws SQLException {
		TableStructConnection tsConnection = tableStructConnection.get();
		if(tsConnection == null) {
			tsConnection = new TableStructConnection(dataSourceWrapper, tableSqlStatementHandler.queryTableExistsSql());
			tableStructConnection.set(tsConnection);
		}
		return tsConnection;
	}
	
	/**
	 * 重置处理器, 主要就是处理连接
	 */
	public void resetting() {
		TableStructConnection connection = tableStructConnection.get();
		if(connection != null) {
			tableStructConnection.remove();
			connection.close();
		}
	}
	
	/**
	 * 表序列化处理器
	 * @return
	 */
	public TableSerializationHandler getTableserializationhandler() {
		return tableSerializationHandler;
	}

	/**
	 * 执行sql语句
	 * @param sql
	 * @throws SQLException 
	 */
	public void executeSql(String sql) throws SQLException {
		getTableStructConnection().executeSql(sql);
	}
	
	
	// -------------------------------------------------------------------------------------------------------------------
	// 创建操作
	// -------------------------------------------------------------------------------------------------------------------
	// 创建表
	private void createTable_(TableMetadata table) throws SQLException {
		executeSql(tableSqlStatementHandler.tableCreateSqlStatement(table));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, tableSqlStatementHandler.tableDropSqlStatement(table.getName()));
	}
	
	// 创建约束
	private void createConstraint(Constraint constraint) throws SQLException {
		executeSql(tableSqlStatementHandler.constraintCreateSqlStatement(constraint));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, tableSqlStatementHandler.constraintDropSqlStatement(constraint));
	}
	private void createConstraints(Collection<Constraint> constraints) throws SQLException {
		if(constraints != null) {
			for (Constraint constraint : constraints) {
				createConstraint(constraint);
			}
		}
	}
	
	// 创建索引
	private void createIndex(Index index) throws SQLException {
		executeSql(index.getCreateSqlStatement());
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, index.getDropSqlStatement());
	}
	private void createIndexes(Collection<Index> indexes) throws SQLException {
		if(indexes != null) {
			for (Index index : indexes) {
				createIndex(index);
			}
		}
	}
	
	// 创建主键序列
	private void createPrimaryKeySequence(PrimaryKeySequence primaryKeySequence) throws SQLException {
		if(primaryKeySequence != null && primaryKeySequence.executeSqlStatement()) {
			executeSql(primaryKeySequence.getCreateSql());
			RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, primaryKeySequence.getDropSql());
		}
	}
	
	
	// -------------------------------------------------------------------------------------------------------------------
	// 同步操作
	// -------------------------------------------------------------------------------------------------------------------
	// 更新表, 返回是否更新了表结构
	private boolean updateTable(TableMetadata newTable) throws Exception {
		TableMetadata oldTable = tableSerializationHandler.deserialize(newTable.getOldName());
		if(isUpdateTable(newTable, oldTable)) {
			// 删除旧表的约束和索引
			dropConstraints(oldTable.getConstraints());
			dropIndexes(oldTable.getIndexes());
			// 对表和列进行同步
			syncTable(newTable, oldTable);
			syncColumns(newTable, oldTable);
			// 创建新表的约束和索引
			createConstraints(newTable.getConstraints());
			createIndexes(newTable.getIndexes());
			return true;
		}
		return false;
	}
	
	// 判断是否更新了表
	private boolean isUpdateTable(TableMetadata newTable, TableMetadata oldTable) {
		return !newTable.getName().equals(oldTable.getName()) 
				|| isUpdateColumns(newTable.getDeclareColumns(), oldTable)
				|| isUpdateConstraints(newTable.getConstraints(), oldTable)
				|| isUpdateIndexes(newTable.getIndexes(), oldTable);
	}
	
	// 判断是否更新了列
	private boolean isUpdateColumns(Collection<ColumnMetadata> columns, TableMetadata oldTable) {
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
				if(isUpdateColumnStruct_(column, oldColumn)) {
					return true;
				}
			}
		}
		return false;
	}
	
	// 判断是否更新了约束
	private boolean isUpdateConstraints(Collection<Constraint> constraints, TableMetadata oldTable) {
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
	// 判断是否更新了索引
	private boolean isUpdateIndexes(Collection<Index> indexes, TableMetadata oldTable) {
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
	private void syncTable(TableMetadata newTable, TableMetadata oldTable) throws SQLException {
		if(!newTable.getName().equals(oldTable.getName())) 
			tableRename(oldTable.getName(), newTable.getName());
	}
	
	// 同步列
	private void syncColumns(TableMetadata newTable, TableMetadata oldTable) throws Exception {
		Collection<ColumnMetadata> columns = newTable.getDeclareColumns();
		ColumnMetadata oldColumn;
		for (ColumnMetadata column : columns) {
			oldColumn = oldTable.getColumnByName(column.getOldName(), false);
			if(oldColumn == null) { // 为空, 标识为新加的列
				createColumn(newTable.getName(), column);
			}else { // 不为空, 可能是修改了列
				if(!column.getName().equals(oldColumn.getName())) {
					columnRename(newTable.getName(), oldColumn.getName(), column.getName());
				}
				if(isUpdateColumnStruct(newTable, column, oldColumn)) {
					updateColumn(newTable.getName(), oldColumn, column);
				}
			}
		}
		
		for (ColumnMetadata oldColumn_ : oldTable.getDeclareColumns()) {
			// 如果在新表中找不到旧的列实例                                     同时, 使用新表中所有列的oldName也找不到旧的列实例
			if(newTable.getColumnByName(oldColumn_.getName(), false) == null && !columnExistsByOldName(columns, oldColumn_.getName())) { 
				dropColumn(newTable.getName(), oldColumn_);
			}
		}
	}
	// 根据旧列名, 判断列是否存在
	private boolean columnExistsByOldName(Collection<ColumnMetadata> columns, String oldColumnName) {
		for (ColumnMetadata column : columns) {
			if(column.getOldName().equals(oldColumnName)) 
				return true;
		}
		return false;
	}

	// 是否修改了列结构(复杂判断)
	private boolean isUpdateColumnStruct(TableMetadata table, ColumnMetadata column, ColumnMetadata oldColumn) throws Exception {
		if(EnvironmentContext.getEnvironmentProperty().enableColumnStructUpdateValidate()) {
			boolean isModifyColumn = false;
			if(column.getDataTypeHandler().unEquals(oldColumn.getDataTypeHandler())) {
				if(!EnvironmentContext.getDialect().getDBFeatures().supportColumnDataTypeConvert(oldColumn.getDBDataType(), column.getDBDataType())) {
					throw new Exception("在数据库["+EnvironmentContext.getDialect().getType()+"]中, 修改["+table.getName()+"]表的["+column.getName()+"]列的数据类型时, 不支持从["+oldColumn.getDBDataType()+"]类型, 改为["+column.getDBDataType()+"]类型");
				}
				isModifyColumn = true;
			}
			if(column.getLength() != oldColumn.getLength()) {
				if(column.getLength() < oldColumn.getLength()) {
					throw new Exception("修改["+table.getName()+"]表的["+column.getName()+"]列的数据长度值时, 新的长度值["+column.getLength()+"]不能小于原长度值["+oldColumn.getLength()+"]");
				}
				isModifyColumn = true;
			}
			if(column.getPrecision() != oldColumn.getPrecision()) {
				if(column.getPrecision() < oldColumn.getPrecision()) {
					throw new Exception("修改["+table.getName()+"]表的["+column.getName()+"]列的数据精度值时, 新的精度值["+column.getPrecision()+"]不能小于原精度值["+oldColumn.getPrecision()+"]");
				}
				isModifyColumn = true;
			}
			if(column.isNullable() != oldColumn.isNullable()) {
				isModifyColumn = true;
			}
			return isModifyColumn;
		}
		return isUpdateColumnStruct_(column, oldColumn);
	}
	// 是否修改了列结构(简单判断)
	private boolean isUpdateColumnStruct_(ColumnMetadata column, ColumnMetadata oldColumn) {
		return column.getDataTypeHandler().unEquals(oldColumn.getDataTypeHandler()) 
				|| column.getLength() != oldColumn.getLength() 
				|| column.getPrecision() != oldColumn.getPrecision() 
				|| column.isNullable() != oldColumn.isNullable();
	}
	
	// 修改表名
	private void tableRename(String originTableName, String targetTableName) throws SQLException {
		executeSql(tableSqlStatementHandler.tableRenameSqlStatement(originTableName, targetTableName));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, tableSqlStatementHandler.tableRenameSqlStatement(targetTableName, originTableName));
	}
	
	// 创建列
	private void createColumn(String tableName, ColumnMetadata column) throws SQLException {
		executeSql(tableSqlStatementHandler.columnCreateSqlStatement(tableName, column));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, tableSqlStatementHandler.columnDropSqlStatement(tableName, column.getName()));
	}
	
	// 删除列
	private void dropColumn(String tableName, ColumnMetadata column) throws SQLException {
		executeSql(tableSqlStatementHandler.columnDropSqlStatement(tableName, column.getName()));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, tableSqlStatementHandler.columnCreateSqlStatement(tableName, column));
	}
	
	// 修改列名
	private void columnRename(String tableName, String originColumnName, String targetColumnName) throws SQLException {
		executeSql(tableSqlStatementHandler.columnRenameSqlStatement(tableName, originColumnName, targetColumnName));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, tableSqlStatementHandler.columnRenameSqlStatement(tableName, targetColumnName, originColumnName));
	}
	
	// 修改列
	private void updateColumn(String tableName, ColumnMetadata originColumn, ColumnMetadata targetColumn) throws SQLException {
		executeSql(tableSqlStatementHandler.columnModifySqlStatement(tableName, targetColumn));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, tableSqlStatementHandler.columnModifySqlStatement(tableName, originColumn));
	}
	
	
	// -------------------------------------------------------------------------------------------------------------------
	// 删除操作
	// -------------------------------------------------------------------------------------------------------------------
	// 删除主键序列
	private void dropPrimaryKeySequence(PrimaryKeySequence primaryKeySequence) throws SQLException {
		if(primaryKeySequence != null && primaryKeySequence.executeSqlStatement()) {
			executeSql(primaryKeySequence.getDropSql());
			RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, primaryKeySequence.getCreateSql());
		}
	}
	
	// 删除索引
	private void dropIndex(Index index) throws SQLException {
		executeSql(index.getDropSqlStatement());
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, index.getCreateSqlStatement());
	}
	private void dropIndexes(Collection<Index> indexes) throws SQLException {
		if(indexes != null) {
			for (Index index : indexes) {
				dropIndex(index);
			}
		}
	}
	
	// 删除约束
	private void dropConstraint(Constraint constraint) throws SQLException {
		executeSql(tableSqlStatementHandler.constraintDropSqlStatement(constraint));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, tableSqlStatementHandler.constraintCreateSqlStatement(constraint));
	}
	private void dropConstraints(Collection<Constraint> constraints) throws SQLException {
		if(constraints != null) {
			for (Constraint constraint : constraints) {
				dropConstraint(constraint);
			}
		}
	}
	
	// 删除表
	private void dropTable(TableMetadata table) throws SQLException {
		executeSql(tableSqlStatementHandler.tableDropSqlStatement(table.getName()));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, tableSqlStatementHandler.tableCreateSqlStatement(table));
	}
	
	
	// -------------------------------------------------------------------------------------------------------------------
	/**
	 * 创建表, 根据table的createMode决定如何同步
	 * @param table
	 * @throws Exception 
	 */
	public void createTable(TableMetadata table) throws Exception {
		if(table.getCreateMode() == CreateMode.NONE)
			return;
		
		if(getTableStructConnection().tableExists(table.getOldName())) {
			switch(table.getCreateMode()) {
				case DROP_CREATE:
					dropPrimaryKeySequence(table.getPrimaryKeySequence());
					dropIndexes(table.getIndexes());
					dropConstraints(table.getConstraints());
					dropTable(table);
					break;
				case DYNAMIC_UPDATE:
					if(updateTable(table))
						tableSerializationHandler.createFile(table);
					return;
				default:
					return;
			}
		}
		createTable_(table);
		createConstraints(table.getConstraints());
		createIndexes(table.getIndexes());
		createPrimaryKeySequence(table.getPrimaryKeySequence());
		
		if(table.getCreateMode() == CreateMode.DYNAMIC_UPDATE)
			tableSerializationHandler.createFile(table);
	}
	
	/**
	 * 删除表
	 * @param table
	 * @throws SQLException 
	 */
	public void deleteTable(TableMetadata table) throws SQLException {
		if(table.getCreateMode() == CreateMode.NONE)
			return;
		
		if(getTableStructConnection().tableExists(table.getOldName())) {
			dropPrimaryKeySequence(table.getPrimaryKeySequence());
			dropIndexes(table.getIndexes());
			dropConstraints(table.getConstraints());
			dropTable(table);
		}
		tableSerializationHandler.deleteFile(table.getName());
	}
}
