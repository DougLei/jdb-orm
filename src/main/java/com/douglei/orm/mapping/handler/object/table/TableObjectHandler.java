package com.douglei.orm.mapping.handler.object.table;

import java.sql.SQLException;
import java.util.Collection;

import com.douglei.orm.dialect.object.pk.sequence.PrimaryKeySequence;
import com.douglei.orm.mapping.handler.object.DBConnection;
import com.douglei.orm.mapping.handler.object.ObjectHandler;
import com.douglei.orm.mapping.handler.rollback.RollbackExecMethod;
import com.douglei.orm.mapping.handler.rollback.RollbackRecorder;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.Constraint;
import com.douglei.orm.mapping.impl.table.metadata.CreateMode;
import com.douglei.orm.mapping.impl.table.metadata.Index;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;

/**
 * 表对象处理器
 * @author DougLei
 */
public class TableObjectHandler extends ObjectHandler<TableMetadata, TableMetadata>{
	
	public TableObjectHandler(DBConnection connection) {
		super(connection);
	}

	// -------------------------------------------------------------------------------------------------------------------
	// 创建操作
	// -------------------------------------------------------------------------------------------------------------------
	// 创建表
	private void createTable(TableMetadata table) throws SQLException {
		connection.executeSql(sqlStatementHandler.createTable(table));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, sqlStatementHandler.dropTable(table.getName()), connection);
	}
	
	// 创建约束
	private void createConstraint(Constraint constraint) throws SQLException {
		connection.executeSql(sqlStatementHandler.createConstraint(constraint));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, sqlStatementHandler.dropConstraint(constraint), connection);
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
		connection.executeSql(index.getCreateSqlStatement());
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, index.getDropSqlStatement(), connection);
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
		if(primaryKeySequence != null && primaryKeySequence.executeSql()) {
			connection.executeSql(primaryKeySequence.getCreateSql());
			RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, primaryKeySequence.getDropSql(), connection);
		}
	}
	
	
	// -------------------------------------------------------------------------------------------------------------------
	// 同步操作
	// -------------------------------------------------------------------------------------------------------------------
	// 更新表, 返回是否更新了表结构
	private void updateTable(TableMetadata newTable, TableMetadata oldTableMetadata) throws Exception {
		if(oldTableMetadata == null) // 如果为null, 有两种情况, 一种是框架启动, 另一个种是数据库中存在表, 而映射容器中不存在; 经过此次处理后, 框架可动态的将已存在的表和本次的新映射关联起来
			return;
		
		if(isUpdateTable(newTable, oldTableMetadata)) {
			// 删除旧表的约束和索引
			dropConstraints(oldTableMetadata.getConstraints());
			dropIndexes(oldTableMetadata.getIndexes());
			// 对表和列进行同步
			syncTable(newTable, oldTableMetadata);
			syncColumns(newTable, oldTableMetadata);
			// 创建新表的约束和索引
			createConstraints(newTable.getConstraints());
			createIndexes(newTable.getIndexes());
		}
	}
	
	// 判断是否更新了表
	private boolean isUpdateTable(TableMetadata newTable, TableMetadata oldTable) throws Exception {
		return !newTable.getName().equals(oldTable.getName()) 
				|| isUpdateColumns(newTable.getDeclareColumns(), oldTable)
				|| isUpdateConstraints(newTable.getConstraints(), oldTable)
				|| isUpdateIndexes(newTable.getIndexes(), oldTable);
	}
	
	// 判断是否更新了列
	private boolean isUpdateColumns(Collection<ColumnMetadata> columns, TableMetadata oldTable) throws Exception {
		if(columns.size() != oldTable.getDeclareColumns().size()) 
			return true;
		
		ColumnMetadata oldColumn = null;
		for (ColumnMetadata column : columns) {
			oldColumn = oldTable.getColumns().get(column.getOldName());
			if(oldColumn == null) {
				return true;
			}else {// 不为空, 标识可能为修改列
				if(!column.getName().equals(oldColumn.getName())) {
					return true;
				}
				if(isUpdateColumnObject(column, oldColumn)) {
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
				if(oldTable.getConstraint(constraint.getName()) == null) {
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
				if(oldTable.getIndex(index.getName()) == null) {
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
		ColumnMetadata oldColumn;
		for (ColumnMetadata newColumn : newTable.getDeclareColumns()) {
			oldColumn = oldTable.getColumns().get(newColumn.getOldName());
			if(oldColumn == null) { // 为空, 标识为新加的列
				createColumn(newTable.getName(), newColumn);
			}else { // 不为空, 可能是修改了列
				if(!newColumn.getName().equals(oldColumn.getName())) 
					columnRename(newTable.getName(), oldColumn.getName(), newColumn.getName());
				if(isUpdateColumnObject(newColumn, oldColumn)) 
					updateColumn(newTable.getName(), oldColumn, newColumn);
			}
		}
		
		for (ColumnMetadata oldColumn_ : oldTable.getDeclareColumns()) {
			// 如果在新表中找不到旧的列实例                                     同时, 使用新表中所有列的oldName也找不到旧的列实例
			if(!newTable.getColumns().containsKey(oldColumn_.getName()) && !columnExistsByOldName(newTable.getDeclareColumns(), oldColumn_.getName())) 
				dropColumn(newTable.getName(), oldColumn_);
		}
	}
	// 根据旧列名, 判断列是否存在
	private boolean columnExistsByOldName(Collection<ColumnMetadata> newColumns, String oldColumnName) {
		for (ColumnMetadata newColumn : newColumns) {
			if(newColumn.getOldName().equals(oldColumnName)) 
				return true;
		}
		return false;
	}

	// 是否修改了列对象
	private boolean isUpdateColumnObject(ColumnMetadata column, ColumnMetadata oldColumn) throws Exception {
		return 	!column.getDBDataType().equals(oldColumn.getDBDataType()) 
				|| column.getLength() != oldColumn.getLength() 
				|| column.getPrecision() != oldColumn.getPrecision() 
				|| column.isNullable() != oldColumn.isNullable();
	}
	
	// 修改表名
	private void tableRename(String originTableName, String targetTableName) throws SQLException {
		connection.executeSql(sqlStatementHandler.renameTable(originTableName, targetTableName));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, sqlStatementHandler.renameTable(targetTableName, originTableName), connection);
	}
	
	// 创建列
	private void createColumn(String tableName, ColumnMetadata column) throws SQLException {
		connection.executeSql(sqlStatementHandler.createColumn(tableName, column));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, sqlStatementHandler.dropColumn(tableName, column.getName()), connection);
	}
	
	// 删除列
	private void dropColumn(String tableName, ColumnMetadata column) throws SQLException {
		connection.executeSql(sqlStatementHandler.dropColumn(tableName, column.getName()));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, sqlStatementHandler.createColumn(tableName, column), connection);
	}
	
	// 修改列名
	private void columnRename(String tableName, String originColumnName, String targetColumnName) throws SQLException {
		connection.executeSql(sqlStatementHandler.renameColumn(tableName, originColumnName, targetColumnName));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, sqlStatementHandler.renameColumn(tableName, targetColumnName, originColumnName), connection);
	}
	
	// 修改列
	private void updateColumn(String tableName, ColumnMetadata originColumn, ColumnMetadata targetColumn) throws SQLException {
		connection.executeSql(sqlStatementHandler.modifyColumn(tableName, targetColumn));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, sqlStatementHandler.modifyColumn(tableName, originColumn), connection);
	}
	
	
	// -------------------------------------------------------------------------------------------------------------------
	// 删除操作
	// -------------------------------------------------------------------------------------------------------------------
	// 删除主键序列
	private void dropPrimaryKeySequence(PrimaryKeySequence primaryKeySequence) throws SQLException {
		if(primaryKeySequence != null && primaryKeySequence.executeSql()) {
			connection.executeSql(primaryKeySequence.getDropSql());
			RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, primaryKeySequence.getCreateSql(), connection);
		}
	}
	
	// 删除索引
	private void dropIndex(Index index) throws SQLException {
		connection.executeSql(index.getDropSqlStatement());
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, index.getCreateSqlStatement(), connection);
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
		connection.executeSql(sqlStatementHandler.dropConstraint(constraint));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, sqlStatementHandler.createConstraint(constraint), connection);
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
		connection.executeSql(sqlStatementHandler.dropTable(table.getName()));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, sqlStatementHandler.createTable(table), connection);
	}
	
	
	// -------------------------------------------------------------------------------------------------------------------
	@Override
	public void create(TableMetadata table, TableMetadata exTableMetadata) throws Exception {
		if(table.getCreateMode() == CreateMode.NONE)
			return;
		
		if(connection.tableExists(table.getOldName())) {
			switch(table.getCreateMode()) {
				case DROP_CREATE:
					dropPrimaryKeySequence(table.getPrimaryKeySequence());
					dropIndexes(table.getIndexes());
					dropConstraints(table.getConstraints());
					dropTable(table);
					break;
				case DYNAMIC_UPDATE:
					if(table.isUpdateName()) 
						validateNameExists(table);
					updateTable(table, exTableMetadata);
					return;
				default:
					return;
			}
		}
		
		validateNameExists(table);
		
		createTable(table);
		createConstraints(table.getConstraints());
		createIndexes(table.getIndexes());
		createPrimaryKeySequence(table.getPrimaryKeySequence());
	}
	
	@Override
	public void delete(TableMetadata table, TableMetadata exTableMetadata) throws SQLException {
		if(table.getCreateMode() == CreateMode.NONE || !connection.tableExists(table.getOldName()))
			return;
		
		dropPrimaryKeySequence(table.getPrimaryKeySequence());
		dropIndexes(table.getIndexes());
		dropConstraints(table.getConstraints());
		dropTable(table);
	}
}
