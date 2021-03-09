package com.douglei.orm.dialect.impl.sqlserver.sqlhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.dialect.sqlhandler.SqlStatementHandler;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.ConstraintMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.sql.query.page.PageSqlStatement;

/**
 * 
 * @author DougLei
 */
public class SqlStatementHandlerImpl extends SqlStatementHandler{
	private static final Logger logger = LoggerFactory.getLogger(SqlStatementHandlerImpl.class);
	
	// --------------------------------------------------------------------------------------------
	// query
	// --------------------------------------------------------------------------------------------
	@Override
	public String queryTableExists() {
		return "select count(1) from sysobjects where id = object_id(?) and type='U'";
	}

	@Override
	public String queryViewExists() {
		return "select count(1) from sysobjects where id = object_id(?) and type='V'";
	}
	
	@Override
	public String queryProcedureExists() {
		return "select count(1) from sysobjects where id = object_id(?) and type='P'";
	}
	
	// --------------------------------------------------------------------------------------------
	// table
	// --------------------------------------------------------------------------------------------
	@Override
	public String createTable(TableMetadata table) {
		StringBuilder sql = new StringBuilder(1000);
		sql.append("create table ").append(table.getName());
		sql.append("(");
		
		String autoincrementPrimaryKeyColumnName = (table.getAutoincrementPrimaryKey()!=null)?table.getAutoincrementPrimaryKey().getColumn():null;
		for (ColumnMetadata column : table.getColumns()) {
			sql.append(column.getName()).append(" ");
			sql.append(column.getDBDataType().getSqlStatement(column.getLength(), column.getPrecision()));
			if(column.getName().equals(autoincrementPrimaryKeyColumnName))
				sql.append(" identity ");
			if(!column.isNullable()) 
				sql.append(" not null");
			sql.append(',');
		}
		sql.setLength(sql.length()-1);
		sql.append(")");
		return sql.toString();
	}

	@Override
	public String renameTable(String oldTableName, String newTableName) {
		StringBuilder tmpSql = new StringBuilder(80);
		tmpSql.append("exec sp_rename '").append(oldTableName).append("','").append(newTableName).append("'");
		return tmpSql.toString();
	}
	
	// --------------------------------------------------------------------------------------------
	// column
	// --------------------------------------------------------------------------------------------
	@Override
	public String renameColumn(String tableName, String oldColumnName, String newColumnName) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("exec sp_rename '").append(tableName).append(".").append(oldColumnName).append("','").append(newColumnName).append("','column'");
		return tmpSql.toString();
	}
	
	@Override
	public String updateColumn(String tableName, ColumnMetadata column) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(tableName).append(" alter column ").append(column.getName()).append(" ");
		tmpSql.append(column.getDBDataType().getSqlStatement(column.getLength(), column.getPrecision())).append(" ");
		if(!column.isNullable()) 
			tmpSql.append("not null");
		return tmpSql.toString();
	}
	
	// --------------------------------------------------------------------------------------------
	// constraint
	// --------------------------------------------------------------------------------------------
	@Override
	protected String createDefaultValue(String tableName, ConstraintMetadata constraint) {
		StringBuilder sql = new StringBuilder(100);
		sql.append("alter table ").append(tableName).append(" add constraint ").append(constraint.getName());
		sql.append(" default ").append(constraint.getDefaultValue());
		sql.append(" for ").append(constraint.getColumnNameList().get(0));
		return sql.toString();
	}
	
	// --------------------------------------------------------------------------------------------
	// sql拼装
	// --------------------------------------------------------------------------------------------
	@Override
	public boolean extractOrderByClause() {
		return true;
	}

	@Override
	public String getPageQuerySql(int pageNum, int pageSize, PageSqlStatement statement) {
		StringBuilder pageQuerySql = new StringBuilder(340 + statement.getTotalLength());
		
		if(statement.getWithClause() != null)
			pageQuerySql.append(statement.getWithClause()).append(' ');
		
		int maxIndex = pageNum*pageSize;
		pageQuerySql.append("SELECT JDB_ORM_THIRD_QUERY_.* FROM (SELECT TOP ");
		pageQuerySql.append(maxIndex);
		pageQuerySql.append(" ROW_NUMBER() OVER(").append((statement.getOrderByClause()==null?"ORDER BY CURRENT_TIMESTAMP":statement.getOrderByClause())).append(") AS RN, JDB_ORM_SECOND_QUERY_.* FROM (");
		pageQuerySql.append(statement.getQuerySQL());
		pageQuerySql.append(") JDB_ORM_SECOND_QUERY_");
		pageQuerySql.append(" ) JDB_ORM_THIRD_QUERY_ WHERE JDB_ORM_THIRD_QUERY_.RN >");
		pageQuerySql.append(maxIndex-pageSize);
		
		logger.debug("进行分页查询的sql语句为: {}", pageQuerySql);	
		return pageQuerySql.toString();
	}
}
