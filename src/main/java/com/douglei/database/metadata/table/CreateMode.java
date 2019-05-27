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
	 * 进行create, 如果表已经存在, 则跳过
	 */
	CREATE;
	
	
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
