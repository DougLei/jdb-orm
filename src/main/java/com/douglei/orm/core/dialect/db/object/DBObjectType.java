package com.douglei.orm.core.dialect.db.object;

/**
 * 数据库对象类型
 * @author DougLei
 */
public enum DBObjectType {
	PROCEDURE("存储过程"),
	VIEW("视图");
	
	private String name;
	private DBObjectType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
