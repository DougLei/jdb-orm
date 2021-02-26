package com.douglei.orm.mapping.impl.table.metadata;

/**
 * 
 * @author DougLei
 */
public enum ConstraintType {

	/**
	 * 主键约束
	 */
	PRIMARY_KEY("PK", "PRIMARY KEY", true),
	/**
	 * 唯一值约束
	 */
	UNIQUE("UQ", "UNIQUE", true),
	/**
	 * 默认值约束
	 */
	DEFAULT_VALUE("DF", "DEFAULT", false),
	/**
	 * 检查约束
	 */
	CHECK("CK", "CHECK", false),
	/**
	 * 外键约束
	 */
	FOREIGN_KEY("FK", "FOREIGN KEY", false);
	
	private String namePrefix;
	private String sqlStatement;
	private boolean supportMultiColumn;// 约束是否支持绑定多个列
	private ConstraintType(String namePrefix, String sqlStatement, boolean supportMultiColumn) {
		this.namePrefix = namePrefix;
		this.sqlStatement = sqlStatement;
		this.supportMultiColumn = supportMultiColumn;
	}
	
	/**
	 * 获取约束名的前缀
	 * @return
	 */
	public String getNamePrefix() {
		return namePrefix;
	}
	
	/**
	 * 获取约束对应的sql语句
	 * @return
	 */
	public String getSqlStatement() {
		return sqlStatement;
	}
	
	/**
	 * 约束是否支持绑定多个列
	 * @return
	 */
	public boolean supportMultiColumn() {
		return supportMultiColumn;
	}
}
