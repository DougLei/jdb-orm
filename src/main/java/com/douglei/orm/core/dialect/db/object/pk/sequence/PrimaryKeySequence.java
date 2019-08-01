package com.douglei.orm.core.dialect.db.object.pk.sequence;

/**
 * 主键序列
 * @author DougLei
 */
public abstract class PrimaryKeySequence {
	
	/**
	 * 获取序列名
	 * @return
	 */
	public abstract String getName();
	
	/**
	 * 获取创建的sql语句
	 * @return
	 */
	public abstract String getCreateSqlStatement();
	
	/**
	 * 获取删除的sql语句
	 * @return
	 */
	public abstract String getDropSqlStatement();
	
	/**
	 * 获取下一个序列值的sql语句
	 * @return
	 */
	public abstract String getNextvalSql();
	
	/**
	 * 获取当前序列值的sql语句
	 * @return
	 */
	public abstract String getCurrvalSql();
}
