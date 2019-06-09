package com.douglei.orm.core.dialect;

/**
 * 事物隔离级别
 * @author DougLei
 */
public enum TransactionIsolationLevel {
	/**
	 * 没有隔离支持
	 */
	NONE(0),
	/**
	 * 读取未提交: 处于此模式下可能会出现脏读、幻象读、不可重复读
	 */
	READ_UNCOMMITTED(1),//
	/**
	 * 读取已提交: 处于此模式下可能会出现幻象读、不可重复读
	 */
	READ_COMMITTED(2),//
	/**
	 * 可重复读: 处于此模式下可能会出现幻象读
	 */
	REPEATABLE_READ(4),//
	/**
	 * 串行: 不会出现幻象读
	 */
	SERIALIZABLE(8),
	/**
	 * 使用jdbc驱动中的默认隔离级别, 传入该常量等于传入null
	 */
	DEFAULT(-1);
	
	/**
	 * 隔离的级别，越高则隔离的效果越强
	 */
	private int level;
	private TransactionIsolationLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}
}
