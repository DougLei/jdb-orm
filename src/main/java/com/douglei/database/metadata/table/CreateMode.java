package com.douglei.database.metadata.table;

/**
 * 表create的模式
 * @author DougLei
 */
public enum CreateMode {
	/**
	 * 不进行任何create
	 */
	NONE,
	/**
	 * 如果表存在, 则跳过; 如果表不存在, 则创建
	 */
	CREATE,
	/**
	 * 如果表存在, 则先删除, 再创建; 如果表不存在, 则直接创建
	 */
	DROP_CREATE;
	
	public static CreateMode toValue(String mode) {
		mode = mode.toUpperCase();
		CreateMode[] cms = CreateMode.values();
		for (CreateMode cm : cms) {
			if(cm.name().equals(mode)) {
				return cm;
			}
		}
		return null;
	}
}
