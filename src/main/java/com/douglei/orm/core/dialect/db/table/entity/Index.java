package com.douglei.orm.core.dialect.db.table.entity;

import java.io.Serializable;
import java.util.Map;

import com.douglei.orm.context.DBRunEnvironmentContext;

/**
 * 索引
 * @author DougLei
 */
public class Index implements Serializable{
	private static final long serialVersionUID = 6729441929256756138L;
	
	private String tableName;// 表名
	
	private String name;// 索引名
	private Map<String, String> createSqlStatements;// create sql语句	<dialect, createSqlStatements>
	private Map<String, String> dropSqlStatements;// drop sql语句
	
	public Index(String tableName, String name, Map<String, String> createSqlStatements, Map<String, String> dropSqlStatements) {
		this.tableName = tableName;
		this.createSqlStatements = createSqlStatements;
		this.dropSqlStatements = dropSqlStatements;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public String getName() {
		return name;
	}
	public String getCreateSqlStatement() {
		return createSqlStatements.get(DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getType().name());
	}
	public String getDropSqlStatement() {
		return dropSqlStatements.get(DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getType().name());
	}
}
