package com.douglei.database.dialect.db.table.entity;

import java.util.Collection;
import java.util.Map;

/**
 * 
 * @author DougLei
 */
public class Table {
	protected String name;// 表名
	protected Map<String, Column> columns;// 列
	protected Map<String, Constraint> constraints;// 约束
	protected Map<String, Index> indexes;// 索引
	
	public Table(String name) {
		this.name = name.toUpperCase();
	}

	public String getName() {
		return name;
	}
	public Collection<Column> getColumns() {
		return columns.values();
	}
}
