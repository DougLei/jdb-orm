package com.douglei.orm.mapping.handler.object;

/**
 * 对象处理器
 * @author DougLei
 */
public abstract class DBObjectHandler {
	protected DBConnection connection;
	
	protected DBObjectHandler(DBConnection connection) {
		this.connection = connection;
	}
}
