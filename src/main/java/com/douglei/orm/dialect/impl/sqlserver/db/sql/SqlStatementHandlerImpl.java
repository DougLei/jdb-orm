package com.douglei.orm.dialect.impl.sqlserver.db.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.dialect.sql.SqlStatementHandler;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.Constraint;
import com.douglei.orm.sql.pagequery.PageSqlStatement;
import com.douglei.orm.sql.pagerecursivequery.PageRecursiveSqlStatement;

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
	public String queryNameExists() {
		return "select count(1) from sysobjects where id = object_id(?)";
	}

	@Override
	public String queryTableExists() {
		return "select count(1) from sysobjects where id = object_id(?) and type='U'";
	}

	@Override
	public String queryViewExists() {
		return "select count(1) from sysobjects where id = object_id(?) and type='V'";
	}
	
	@Override
	public String queryProcExists() {
		return "select count(1) from sysobjects where id = object_id(?) and type='P'";
	}
	
	@Override
	public String queryViewScript() {
		return "select b.definition from sysobjects a left join sys.sql_modules b on a.id=b.object_id where a.type='V' and a.name=?";
	}
	
	@Override
	public String queryProcedureScript() {
		return "select b.definition from sysobjects a left join sys.sql_modules b on a.id=b.object_id where a.type='P' and a.name=?";
	}
	
	// --------------------------------------------------------------------------------------------
	// table
	// --------------------------------------------------------------------------------------------
	@Override
	protected String primaryKeySequenceSqlKeyword() {
		return "identity";
	}

	@Override
	public String renameTable(String originTableName, String targetTableName) {
		StringBuilder tmpSql = new StringBuilder(80);
		tmpSql.append("exec sp_rename '").append(originTableName).append("','").append(targetTableName).append("'");
		return tmpSql.toString();
	}
	
	// --------------------------------------------------------------------------------------------
	// column
	// --------------------------------------------------------------------------------------------
	@Override
	public String renameColumn(String tableName, String originColumnName, String targetColumnName) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("exec sp_rename '").append(tableName).append(".").append(originColumnName).append("','").append(targetColumnName).append("','column'");
		return tmpSql.toString();
	}
	
	@Override
	public String modifyColumn(String tableName, ColumnMetadata column) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(tableName).append(" alter column ").append(column.getName()).append(" ");
		tmpSql.append(column.getDataType().mappedDBDataType().getSqlStatement(column.getLength(), column.getPrecision())).append(" ");
		if(!column.isNullable()) {
			tmpSql.append("not null");
		}
		return tmpSql.toString();
	}
	
	// --------------------------------------------------------------------------------------------
	// constraint
	// --------------------------------------------------------------------------------------------
	@Override
	protected String createDefaultValue(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" add constraint ").append(constraint.getName());
		tmpSql.append(" default ").append(constraint.getDefaultValue());
		tmpSql.append(" for ").append(constraint.getConstraintColumnNames());
		return tmpSql.toString();
	}
	
	// --------------------------------------------------------------------------------------------
	// sql拼装
	// --------------------------------------------------------------------------------------------
	@Override
	public boolean needExtractOrderByClause() {
		return true;
	}

	@Override
	public String getPageQuerySql(int pageNum, int pageSize, PageSqlStatement statement) {
		int maxIndex = pageNum*pageSize;
		
		StringBuilder pageQuerySql = new StringBuilder(340 + statement.length());
		if(statement.getWithClause() != null)
			pageQuerySql.append(statement.getWithClause()).append(' ');
		pageQuerySql.append("SELECT JDB_ORM_THIRD_QUERY_.* FROM (SELECT TOP ");
		pageQuerySql.append(maxIndex);
		pageQuerySql.append(" ROW_NUMBER() OVER(").append((statement.getOrderByClause()==null?"ORDER BY CURRENT_TIMESTAMP":statement.getOrderByClause())).append(") AS RN, JDB_ORM_SECOND_QUERY_.* FROM (");
		pageQuerySql.append(statement.getSql());
		pageQuerySql.append(") JDB_ORM_SECOND_QUERY_");
		if(statement instanceof PageRecursiveSqlStatement) { // 分页递归查询
			pageQuerySql.append(" WHERE ");
			appendConditionSql2RecursiveSql(pageQuerySql, (PageRecursiveSqlStatement)statement);
		}
		pageQuerySql.append(" ) JDB_ORM_THIRD_QUERY_ WHERE JDB_ORM_THIRD_QUERY_.RN >");
		pageQuerySql.append(maxIndex-pageSize);
		if(logger.isDebugEnabled()) 
			logger.debug("{} 进行分页查询的sql语句为: {}", getClass().getName(), pageQuerySql);
		return pageQuerySql.toString();
	}
}
