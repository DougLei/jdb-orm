package com.douglei.orm.mapping.handler.object;

import java.sql.SQLException;
import java.util.HashSet;

import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.orm.dialect.DatabaseNameConstants;
import com.douglei.orm.dialect.sqlhandler.SqlStatementHandler;
import com.douglei.orm.mapping.handler.rollback.RollbackExecMethod;
import com.douglei.orm.mapping.handler.rollback.RollbackRecorder;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.ConstraintMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;

/**
 * 表对象处理器
 * @author DougLei
 */
public class TableObjectHandler extends DBObjectHandler{
	private SqlStatementHandler SQLStatementHandler;
	
	public TableObjectHandler(DBConnection connection) {
		super(connection);
		this.SQLStatementHandler = EnvironmentContext.getEnvironment().getDialect().getSqlStatementHandler();
	}

	// -------------------------------------------------------------------------------------------------------------------
	// 创建操作
	// -------------------------------------------------------------------------------------------------------------------
	// 创建表
	private void createTable(TableMetadata table) throws SQLException {
		connection.executeSql(SQLStatementHandler.createTable(table));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, SQLStatementHandler.dropTable(table.getName()), connection);
	}
	
	// 创建约束
	private void createConstraint(String tableName, ConstraintMetadata constraint) throws SQLException {
		connection.executeSql(SQLStatementHandler.createConstraint(tableName, constraint));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, SQLStatementHandler.dropConstraint(tableName, constraint), connection);
	}
	private void createConstraints(TableMetadata table) throws SQLException {
		if(table.getConstraints() != null) {
			for (ConstraintMetadata constraint : table.getConstraints()) 
				createConstraint(table.getName(), constraint);
		}
	}
	
	// 创建自增主键
	private void createAutoincrementPrimaryKey(TableMetadata table) throws SQLException {
		if(table.getAutoincrementPrimaryKey() != null) {
			String sql = SQLStatementHandler.createAutoincrementPrimaryKey(table);
			if(sql != null) {
				connection.executeSql(sql);
				RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, SQLStatementHandler.dropAutoincrementPrimaryKey(table), connection);
			}
		}
	}
	
	
	// -------------------------------------------------------------------------------------------------------------------
	// 同步操作
	// -------------------------------------------------------------------------------------------------------------------
	// 更新表, 返回是否更新了表结构
	private void updateTable(TableMetadata newTable, TableMetadata oldTable) throws Exception {
		if(!isUpdateTable(newTable, oldTable))
			return;
		
		// 删除旧表的约束
		dropConstraints(oldTable);
		
		// 对表和列进行同步
		syncTable(newTable, oldTable);
		syncColumns(newTable, oldTable);
		
		// 创建新表的约束
		createConstraints(newTable);
	}
	
	// 判断是否更新了表
	private boolean isUpdateTable(TableMetadata newTable, TableMetadata oldTable) throws Exception {
		return !newTable.getName().equals(oldTable.getName()) 
				|| isUpdateColumns(newTable, oldTable)
				|| isUpdateConstraints(newTable, oldTable);
	}
	// 判断是否更新了列
	private boolean isUpdateColumns(TableMetadata newTable, TableMetadata oldTable) throws Exception {
		if(newTable.getColumns().size() != oldTable.getColumns().size()) 
			return true;
		
		ColumnMetadata oldColumn = null;
		for(ColumnMetadata newColumn : newTable.getColumns()) {
			oldColumn = oldTable.getColumnMap4Name().get(newColumn.getOldName());
			
			// 判断是否添加列
			if(oldColumn == null)
				return true;
			
			// 判断是否修改列
			if(!newColumn.getName().equals(oldColumn.getName()) || isUpdateColumn(newColumn, oldColumn)) 
				return true;
		}
		
		// 判断是否删除列
		for(ColumnMetadata deleteOldColumn : oldTable.getColumns()) {
			if(!newTable.getColumnMap4OldName().containsKey(deleteOldColumn.getName()))
				return true;
		}
		return false;
	}
	// 是否修改了列
	private boolean isUpdateColumn(ColumnMetadata newColumn, ColumnMetadata oldColumn) throws Exception {
		return 	!newColumn.getDBDataType().equals(oldColumn.getDBDataType()) 
				|| newColumn.getLength() != oldColumn.getLength() 
				|| newColumn.getPrecision() != oldColumn.getPrecision() 
				|| newColumn.isNullable() != oldColumn.isNullable();
	}
	// 判断是否更新了约束
	private boolean isUpdateConstraints(TableMetadata newTable, TableMetadata oldTable) {
		if(newTable.getConstraints() == null) {
			if(oldTable.getConstraints() != null) 
				return true;
			return false;
		}
		
		if(oldTable.getConstraints() == null || newTable.getConstraints().size() != oldTable.getConstraints().size()) 
			return true;
		
		// 判断新旧表的约束名是否完全一致
		if(!newTable.getConstraintMap4Name().keySet().equals(oldTable.getConstraintMap4Name().keySet()))
			return true;
		
		ConstraintMetadata oldConstraint = null;
		for (ConstraintMetadata newConstraint : newTable.getConstraints()) {
			oldConstraint = oldTable.getConstraintMap4Name().get(newConstraint.getName());
			if(newConstraint.getType() != oldConstraint.getType()) // 修改了约束类型(因为约束名是可配置的, 所以不能单纯用约束名前缀和约束名判断类型是否被修改)
				return true;
			
			// 修改了约束的列
			if(newConstraint.getColumnNameList().size() != oldConstraint.getColumnNameList().size()) 
				return true;
			if(newConstraint.getColumnNameList().size() == 1) {
				if(!newConstraint.getColumnNameList().get(0).equals(oldConstraint.getColumnNameList().get(0)))
					return true;
			}else {
				if(!new HashSet<String>(newConstraint.getColumnNameList()).equals(new HashSet<String>(oldConstraint.getColumnNameList())))
					return true;
			}
			
			// 修改了约束信息
			switch(newConstraint.getType()) {
				case AUTO_INCREMENT_PRIMARY_KEY:
					if(EnvironmentContext.getEnvironment().getDialect().getDatabaseType().getName().equals(DatabaseNameConstants.ORACLE)) {
						if(!newConstraint.getSequence().equals(oldConstraint.getSequence()))
							return true;
					}
					break;
				case PRIMARY_KEY:
				case UNIQUE:
					break;
				case DEFAULT_VALUE:
					if(!newConstraint.getDefaultValue().equals(oldConstraint.getDefaultValue()))
						return true;
					break;
				case CHECK:
					if(!newConstraint.getCheck().equals(oldConstraint.getCheck()))
						return true;
					break;
				case FOREIGN_KEY:
					if(!newConstraint.getTable().equals(oldConstraint.getTable()) || !newConstraint.getColumn().equals(oldConstraint.getColumn()))
						return true;
					break;
			}
		}
		return false;
	}
	
	// 同步表
	private void syncTable(TableMetadata newTable, TableMetadata oldTable) throws SQLException {
		if(!newTable.getName().equals(oldTable.getName())) 
			renameTable(oldTable.getName(), newTable.getName());
	}
	
	// 同步列 
	private void syncColumns(TableMetadata newTable, TableMetadata oldTable) throws Exception {
		// 添加或修改列
		ColumnMetadata oldColumn = null;
		for(ColumnMetadata newColumn : newTable.getColumns()) {
			oldColumn = oldTable.getColumnMap4Name().get(newColumn.getOldName());
			
			// 添加列
			if(oldColumn == null) { 
				createColumn(newTable.getName(), newColumn);
				continue;
			}
			
			// 修改列
			if(!newColumn.getName().equals(oldColumn.getName())) 
				renameColumn(newTable.getName(), oldColumn.getName(), newColumn.getName());
			if(isUpdateColumn(newColumn, oldColumn)) 
				updateColumn(newTable.getName(), oldColumn, newColumn);
		}
		
		// 删除列
		for(ColumnMetadata deleteOldColumn : oldTable.getColumns()) {
			if(!newTable.getColumnMap4OldName().containsKey(deleteOldColumn.getName()))
				dropColumn(newTable.getName(), deleteOldColumn);
		}
	}

	// 修改表名
	private void renameTable(String oldTableName, String newTableName) throws SQLException {
		connection.executeSql(SQLStatementHandler.renameTable(oldTableName, newTableName));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, SQLStatementHandler.renameTable(newTableName, oldTableName), connection);
	}
	
	// 创建列
	private void createColumn(String tableName, ColumnMetadata column) throws SQLException {
		connection.executeSql(SQLStatementHandler.createColumn(tableName, column));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, SQLStatementHandler.dropColumn(tableName, column.getName()), connection);
	}
	
	// 删除列
	private void dropColumn(String tableName, ColumnMetadata column) throws SQLException {
		connection.executeSql(SQLStatementHandler.dropColumn(tableName, column.getName()));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, SQLStatementHandler.createColumn(tableName, column), connection);
	}
	
	// 修改列名
	private void renameColumn(String tableName, String oldColumnName, String newColumnName) throws SQLException {
		connection.executeSql(SQLStatementHandler.renameColumn(tableName, oldColumnName, newColumnName));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, SQLStatementHandler.renameColumn(tableName, newColumnName, oldColumnName), connection);
	}
	
	// 修改列
	private void updateColumn(String tableName, ColumnMetadata oldColumn, ColumnMetadata newColumn) throws SQLException {
		connection.executeSql(SQLStatementHandler.updateColumn(tableName, newColumn));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, SQLStatementHandler.updateColumn(tableName, oldColumn), connection);
	}
	
	
	// -------------------------------------------------------------------------------------------------------------------
	// 删除操作
	// -------------------------------------------------------------------------------------------------------------------
	// 删除自增主键
	private void dropAutoincrementPrimaryKey(TableMetadata table) throws SQLException {
		if(table.getAutoincrementPrimaryKey() != null) {
			String sql = SQLStatementHandler.dropAutoincrementPrimaryKey(table);
			if(sql != null) {
				connection.executeSql(sql);
				RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, SQLStatementHandler.createAutoincrementPrimaryKey(table), connection);
			}
		}
	}
	
	// 删除约束
	private void dropConstraint(String tableName, ConstraintMetadata constraint) throws SQLException {
		connection.executeSql(SQLStatementHandler.dropConstraint(tableName, constraint));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, SQLStatementHandler.createConstraint(tableName, constraint), connection);
	}
	private void dropConstraints(TableMetadata table) throws SQLException {
		if(table.getConstraints() != null) {
			for (ConstraintMetadata constraint : table.getConstraints()) 
				dropConstraint(table.getName(), constraint);
		}
	}
	
	// 删除表
	private void dropTable(TableMetadata table) throws SQLException {
		connection.executeSql(SQLStatementHandler.dropTable(table.getName()));
		RollbackRecorder.record(RollbackExecMethod.EXEC_DDL_SQL, SQLStatementHandler.createTable(table), connection);
	}
	
	
	// -------------------------------------------------------------------------------------------------------------------
	/**
	 * 创建表
	 * @param addTableMetadata 当前要创建的表元数据实例
	 * @param coveredTableMetadata 被覆盖的表元数据实例, 如果是新添加的表, 或框架启动时, 该参数为null
	 * @throws Exception
	 */
	public void create(TableMetadata addTableMetadata, TableMetadata coveredTableMetadata) throws Exception {
		if(connection.tableExists(addTableMetadata.getOldName())) {
			switch(addTableMetadata.getCreateMode()) {
				case NONE: 
				case CREATE:
					return;
				case DROP_CREATE:
					if(coveredTableMetadata == null) 
						delete(addTableMetadata);
					else 
						delete(coveredTableMetadata);
					break;
				case DYNAMIC:
					if(coveredTableMetadata != null) // 框架启动, 或数据库中存在表而框架映射容器中不存在对应映射(通过这个操作, 可将数据库中的表和映射关联起来)
						updateTable(addTableMetadata, coveredTableMetadata);
					return;
			}
		}
		
		createTable(addTableMetadata);
		createConstraints(addTableMetadata);
		createAutoincrementPrimaryKey(addTableMetadata);
	}
	
	/**
	 * 删除表
	 * @param deletedTableMetadata
	 * @throws SQLException
	 */
	public void delete(TableMetadata deletedTableMetadata) throws SQLException {
		dropAutoincrementPrimaryKey(deletedTableMetadata);
		dropConstraints(deletedTableMetadata);
		dropTable(deletedTableMetadata);
	}
}
