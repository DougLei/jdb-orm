package com.douglei.orm.core.metadata.table;

import java.io.Serializable;
import java.util.Map;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.core.dialect.DialectType;

/**
 * 索引
 * @author DougLei
 */
public class Index implements Serializable{
	private static final long serialVersionUID = -558201651567272757L;

	private String tableName;// 表名
	
	private String name;// 索引名
	private Map<DialectType, String> createSqlStatements;// create sql语句	<dialect, createSqlStatements>
	private Map<DialectType, String> dropSqlStatements;// drop sql语句
	
	public Index(String tableName, String name, Map<DialectType, String> createSqlStatements, Map<DialectType, String> dropSqlStatements) {
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
		return createSqlStatements.get(EnvironmentContext.getDialect().getType());
	}
	public String getDropSqlStatement() {
		return dropSqlStatements.get(EnvironmentContext.getDialect().getType());
	}
}
