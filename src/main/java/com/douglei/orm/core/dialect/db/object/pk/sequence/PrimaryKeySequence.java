package com.douglei.orm.core.dialect.db.object.pk.sequence;

import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.tools.utils.StringUtil;

/**
 * 主键序列
 * @author DougLei
 */
public abstract class PrimaryKeySequence {
	private String name;// 序列名
	protected String createSql;
	protected String dropSql;
	
	public PrimaryKeySequence(String name, String tableName, String createSql, String dropSql) {
		setName(name, tableName);
		this.createSql = StringUtil.isEmpty(createSql)?defaultCreateSql():createSql;
		this.dropSql = StringUtil.isEmpty(dropSql)?defaultDropSql():dropSql;
	}


	private void setName(String name, String tableName) {
		if(StringUtil.isEmpty(name)) {
			name = "PKSEQ_" + tableName;
		}
		this.name = name;
		DBRunEnvironmentContext.getDialect().getDBObjectHandler().validateDBObjectName(this.name);
	}

	/**
	 * 获取序列名
	 * @return
	 */
	public String getName() {
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
	 * 默认的创建sql语句
	 * @return
	 */
	protected abstract String defaultCreateSql();

	/**
	 * 默认的删除sql语句
	 * @return
	 */
	protected abstract String defaultDropSql();
	
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
