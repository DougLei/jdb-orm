package com.douglei.orm.configuration.environment.datasource;

import java.sql.Connection;

/**
 * 事物隔离级别
 * @author DougLei
 */
public enum TransactionIsolationLevel {
	/**
	 * 没有隔离支持
	 */
	NONE(Connection.TRANSACTION_NONE),
	
	/**
	 * 读取未提交: 处于此模式下可能会出现脏读、幻象读、不可重复读
	 */
	READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
	
	/**
	 * 读取已提交: 处于此模式下可能会出现幻象读、不可重复读
	 */
	READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
	
	/**
	 * 可重复读: 处于此模式下可能会出现幻象读
	 */
	REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
	
	/**
	 * 串行: 不会出现幻象读
	 */
	SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE),
	
	/**
	 * 使用jdbc驱动中的默认隔离级别
	 */
	DEFAULT(-1);
	
	/**
	 * 隔离的级别，越高则隔离的效果越强
	 */
	private int level;
	private TransactionIsolationLevel(int level) {
		this.level = level;
	}

	/**
	 * 获取隔离级别
	 * @return
	 */
	public int getLevel() {
		return level;
	}
}
