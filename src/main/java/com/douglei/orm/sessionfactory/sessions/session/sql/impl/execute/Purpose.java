package com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute;

/**
 * SqlExecuteHandler 的用途
 * @author DougLei
 */
public enum Purpose {
	/**
	 * 查询用途
	 */
	QUERY,
	
	/**
	 * 更新用途
	 */
	UPDATE,
	
	/**
	 * 存储过程用途
	 */
	PROCEDURE,
	
	/**
	 * 未知用途
	 */
	UNKNOW;
}
