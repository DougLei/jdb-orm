package com.douglei.database.dialect;

import java.util.Arrays;

/**
 * 事物隔离级别
 * @author DougLei
 */
public enum TransactionIsolationLevel {
	/**
	 * 没有隔离支持
	 */
	NONE(-1),
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
	REPEATABLE_READ(3),//
	/**
	 * 串行: 不会出现幻象读
	 */
	SERIALIZABLE(4);
	
	/**
	 * 隔离的级别，越高则隔离的效果越强
	 */
	private int level;
	private TransactionIsolationLevel(int level) {
		this.level = level;
	}

	public static TransactionIsolationLevel toValue(String value) {
		value = value.trim().toUpperCase();
		
		TransactionIsolationLevel[] tils = TransactionIsolationLevel.values();
		for (TransactionIsolationLevel transactionIsolationLevel : tils) {
			if(transactionIsolationLevel.name().equals(value)) {
				return transactionIsolationLevel;
			}
		}
		throw new IllegalArgumentException("配置的值[\""+value+"\"]错误, 目前支持的值包括：["+Arrays.toString(tils)+"]");
	}

	public int getLevel() {
		return level;
	}
}
