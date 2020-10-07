package com.douglei.orm.mapping.impl.table.metadata;

import java.io.Serializable;

/**
 * 索引
 * @author DougLei
 */
public class Index implements Serializable{
	private String tableName;// 表名
	private String name;// 索引名
	
	private String createSqlStatement;// create sql语句
	private String dropSqlStatement;// drop sql语句
	
	public Index(String tableName, String name, String createSqlStatement, String dropSqlStatement) {
		this.tableName = tableName;
		this.createSqlStatement = createSqlStatement;
		this.dropSqlStatement = dropSqlStatement;
	}
	
	public String getTableName() {
		return tableName;
	}
	public String getName() {
		return name;
	}
	public String getCreateSqlStatement() {
		return createSqlStatement;
	}
	public String getDropSqlStatement() {
		return dropSqlStatement;
	}
}
