package com.douglei.database.metadata.table;

/**
 * 
 * @author DougLei
 */
public enum CreateMode {
	/**
	 * 不进行任何create
	 */
	NONE,
	/**
	 * 进行create, 如果表已经存在, 抛出异常
	 */
	CREATE,
	/**
	 * 先进行drop, 再进行create, 如果drop不存在的表, 抛出异常
	 */
	DROP_CREATE,
	/**
	 * 进行create, 如果表已经存在, 则跳过
	 */
	AUTO_CREATE,
	/**
	 * 先进行drop, 再进行create, 如果drop不存在的表, 则跳过
	 */
	AUTO_DROP_CREATE;
	
	
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
