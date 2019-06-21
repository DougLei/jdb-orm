package com.douglei.orm.core.dialect.db.table.entity;

import com.douglei.tools.utils.StringUtil;

/**
 * 索引
 * @author DougLei
 */
public class Index {
	private String name;// 索引名
	private String createSqlStatement;// create sql语句
	private String dropSqlStatement;// drop sql语句
	
	public Index(String name, String createSqlStatement, String dropSqlStatement) {
		validate(name, createSqlStatement, dropSqlStatement);
		this.name = name;
		this.createSqlStatement = createSqlStatement;
		this.dropSqlStatement = dropSqlStatement;
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
