package com.douglei.orm.mapping.impl.table.metadata;

/**
 * 
 * @author DougLei
 */
public enum ConstraintType {

	/**
	 * 自增主键约束
	 */
	AUTO_INCREMENT_PRIMARY_KEY("PK", "PRIMARY KEY", false) {
		@Override
		public boolean isPrimaryKey() {
			return true;
		}
	},
	/**
	 * 主键约束
	 */
	PRIMARY_KEY("PK", "PRIMARY KEY", true) {
		@Override
		public boolean isPrimaryKey() {
			return true;
		}
	},
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
	private String sqlKey;
	private boolean supportMultiColumn;// 约束是否支持绑定多个列
	private ConstraintType(String namePrefix, String sqlKey, boolean supportMultiColumn) {
		this.namePrefix = namePrefix;
		this.sqlKey = sqlKey;
		this.supportMultiColumn = supportMultiColumn;
	}
	
	/**
	 * 是否主键约束
	 * @return
	 */
	public boolean isPrimaryKey() {
		return false;
	}
	
	/**
	 * 获取约束名的前缀
	 * @return
	 */
	public String getNamePrefix() {
		return namePrefix;
	}
	
	/**
	 * 获取约束对应的sql语句关键字
	 * @return
	 */
	public String getSqlKey() {
		return sqlKey;
	}
	
	/**
	 * 约束是否支持绑定多个列
	 * @return
	 */
	public boolean supportMultiColumn() {
		return supportMultiColumn;
	}
}
