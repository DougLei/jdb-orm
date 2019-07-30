package com.douglei.orm.core.metadata.table;

import com.douglei.tools.utils.StringUtil;

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
	
	private String constraintPrefix;
	private String sqlStatement;
	private boolean supportMultipleColumn;// 是否支持多列, 即复合约束
	private ConstraintType(String constraintPrefix, String sqlStatement, boolean supportMultipleColumn) {
		this.constraintPrefix = constraintPrefix;
		this.sqlStatement = sqlStatement;
		this.supportMultipleColumn = supportMultipleColumn;
	}
	
	public String getConstraintPrefix() {
		return constraintPrefix;
	}
	public String getSqlStatement() {
		return sqlStatement;
	}
	public boolean supportMultipleColumn() {
		return supportMultipleColumn;
	}

	public static ConstraintType toValue(String type) {
		if(StringUtil.notEmpty(type)) {
			type = type.toUpperCase();
			ConstraintType[] cts = ConstraintType.values();
			for (ConstraintType ct : cts) {
				if(ct.name().equals(type)) {
					return ct;
				}
			}
		}
		return null;
	}
}
