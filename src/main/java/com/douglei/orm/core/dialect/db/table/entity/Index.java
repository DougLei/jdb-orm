package com.douglei.orm.core.dialect.db.table.entity;

import com.douglei.tools.utils.StringUtil;

/**
 * 索引
 * TODO 索引目前只是实现了配置索引名(name), 以及索引的[create/drop]sql语句(createSqlStatement/dropSqlStatement), 后续可能完善, 会去实现索引的配置
 * @author DougLei
 */
public class Index {
	private IndexType indexType;
	private String tableName;// 表名
	
//	private Map<String, Column> columns;// 相关的列集合 // TODO 现在还未实现根据配置生成索引, 所以暂时不需要添加相关的列
	
	private String name;// 索引名
	private String createSqlStatement;// create sql语句
	private String dropSqlStatement;// drop sql语句
	
	public Index(IndexType indexType, String tableName, String name, String createSqlStatement, String dropSqlStatement) {
		validate(name, createSqlStatement, dropSqlStatement);
		this.indexType = indexType;
		this.tableName = tableName;
	}
	
	private void validate(String name, String createSqlStatement, String dropSqlStatement) {
		if(StringUtil.isEmpty(name)) {
			throw new NullPointerException("索引名不能为空");
		}
		if(StringUtil.isEmpty(createSqlStatement)) {
			throw new NullPointerException("创建索引[" + name + "]的sql语句不能为空");
		}
		if(StringUtil.isEmpty(dropSqlStatement)) {
			throw new NullPointerException("删除索引[" + name + "]的sql语句不能为空");
		}
	}

	public String getTableName() {
		return tableName;
	}
	public IndexType getIndexType() {
		return indexType;
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
