package com.douglei.database.dialect.impl.mysql.db.table;

import java.util.List;

import com.douglei.database.dialect.db.table.TableSqlStatementHandler;
import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.database.metadata.table.column.extend.ColumnConstraint;
import com.douglei.database.metadata.table.column.extend.ColumnIndex;

/**
 * 
 * @author DougLei
 */
public class TableSqlStatementHandlerImpl extends TableSqlStatementHandler{

	@Override
	protected short tableExists(String tableName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected String tableCreateSqlStatement(String tableName, List<ColumnMetadata> columns) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] columnCreateSqlStatement(String tableName, List<ColumnMetadata> columns) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] columnDropSqlStatement(String tableName, List<ColumnMetadata> columns) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] columnRenameSqlStatement(String tableName, List<ColumnMetadata> columns) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] constraintCreateSqlStatement(String tableName, List<ColumnConstraint> constraints) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] indexCreateSqlStatement(String tableName, List<ColumnIndex> indexes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] indexDropSqlStatement(String tableName, List<ColumnIndex> indexes) {
		// TODO Auto-generated method stub
		return null;
	}
}
