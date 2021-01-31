package com.douglei.orm.mapping.impl.table.metadata;

import com.douglei.tools.StringUtil;

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
	private boolean supportComposite;// 是否支持复合约束
	private ConstraintType(String constraintPrefix, String sqlStatement, boolean supportComposite) {
		this.constraintPrefix = constraintPrefix;
		this.sqlStatement = sqlStatement;
		this.supportComposite = supportComposite;
	}
	
	public String getConstraintPrefix() {
		return constraintPrefix;
	}
	public String getSqlStatement() {
		return sqlStatement;
	}
	public boolean supportComposite() {
		return supportComposite;
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
