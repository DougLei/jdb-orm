package com.douglei.database.dialect.db.table.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库对象
 * @author DougLei
 */
public abstract class DBObject {
	protected String name;// (前缀+表名+列名)
	protected Map<String, Column> columns;// 相关的列集合
	protected String tableName;// 表名
	
	public DBObject(String tableName) {
		this(tableName, null);
	}
	public DBObject(String tableName, Column column) {
		this.tableName = tableName;
		addColumn(column);
	}
	
	/**
	 * 添加约束列
	 * @param column
	 */
	public void addColumn(Column column) {
		if(column == null) {
			return;
		}
		if(columns == null) {
			columns = new HashMap<String, Column>(4);
		}else if(columns.containsKey(column.getName())) {
			throw new ConstraintException("在同一个"+getObjectName()+"中, 出现重复的列["+column.getName()+"]");
		}
		columns.put(column.getName(), column);
	}
	
	private boolean dbObjectUnProcessed=true;// 是否未被处理
	protected void process() {
		if(dbObjectUnProcessed) {
			if(columns == null) {
				throw new NullPointerException("在"+getObjectName()+"中, 关联的列不能为空");
			}
			processDBObject();
			dbObjectUnProcessed = false;
		}
	}
	
	/**
	 * 数据库对象的名称
	 * @return
	 */
	protected abstract String getObjectName();
	
	/**
	 * 处理对象
	 */
	protected abstract void processDBObject();
	
	public String getName() {
		process();
		return name;
	}
	public Collection<Column> getColumns(){
		process();
		return columns.values();
	}
	public String getTableName() {
		process();
		return tableName;
	}
}
