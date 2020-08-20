package com.douglei.orm.core.dialect.db.object.pk.sequence;

import java.io.Serializable;

import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.tools.utils.StringUtil;

/**
 * 主键序列
 * @author DougLei
 */
public abstract class PrimaryKeySequence implements Serializable{
	private static final long serialVersionUID = 8319486112052490770L;
	private String name;// 序列名
	private String createSql;
	private String dropSql;
	
	// processName 用来标识是否处理序列名
	protected PrimaryKeySequence(boolean processName, String name, String createSql, String dropSql, String tableName, ColumnMetadata primaryKeyColumn) {
		setName(processName, name, tableName);
		this.createSql = processCreateSql(createSql, tableName, primaryKeyColumn);
		this.dropSql = processDropSql(dropSql, tableName, primaryKeyColumn);
	}

	private void setName(boolean processName, String name, String tableName) {
		if(processName) {
			if(StringUtil.isEmpty(name)) {
				name = "PKSEQ_" + tableName;
			}
			this.name = name;
			EnvironmentContext.getDialect().getDBObjectHandler().validateDBObjectName(this.name);
		}
	}

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
	public boolean executeSqlStatement() {
		return true;
	}
	
	/**
	 * 处理创建sql语句
	 * @param createSql
	 * @param tableName
	 * @param primaryKeyColumn
	 * @return
	 */
	protected abstract String processCreateSql(String createSql, String tableName, ColumnMetadata primaryKeyColumn);

	/**
	 * 处理删除sql语句
	 * @param dropSql
	 * @param tableName
	 * @param primaryKeyColumn
	 * @return
	 */
	protected abstract String processDropSql(String dropSql, String tableName, ColumnMetadata primaryKeyColumn);
	
	/**
	 * 获取下一个序列值的sql语句
	 * @return
	 */
	public String getNextvalSql() {
		return null;
	}
	
	/**
	 * 获取当前序列值的sql语句
	 * @return
	 */
	public String getCurrvalSql() {
		return null;
	}
}
