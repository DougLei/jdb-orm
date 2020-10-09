package com.douglei.orm.dialect.object.pk.sequence;

import java.io.Serializable;

/**
 * 主键序列
 * @author DougLei
 */
public abstract class PrimaryKeySequence implements Serializable{
	private static final long serialVersionUID = 6777082974190751849L;
	
	protected String name;// 序列名
	protected String createSql;
	protected String dropSql;
	
	/**
	 * 获取序列名
	 * @return
	 */
	public final String getName() {
		return name;
	}
	
	/**
	 * 获取创建的sql语句
	 * @return
	 */
	public final String getCreateSql() {
		return createSql;
	}
	
	/**
	 * 获取删除的sql语句
	 * @return
	 */
	public final String getDropSql() {
		return dropSql;
	}
	
	/**
	 * 序列是否需要执行sql语句(创建与删除序列的sql语句)
	 * @return
	 */
	public boolean executeSql() {
		return true;
	}
}
