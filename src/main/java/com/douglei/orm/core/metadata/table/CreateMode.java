package com.douglei.orm.core.metadata.table;

/**
 * 表create的模式
 * @author DougLei
 */
public enum CreateMode {
	/**
	 * 不进行任何处理
	 */
	NONE,
	/**
	 * 【默认】如果表存在, 则跳过; 如果表不存在, 则创建
	 */
	CREATE,
	/**
	 * 如果表存在, 则先删除, 再创建; 如果表不存在, 则直接创建
	 */
	DROP_CREATE,
	/**
	 * 动态更新, 系统会将当前的表配置和数据库中对应的表比对, 将最新的配置信息同步到数据库的表上
	 * <b>不建议在生产环境中使用</b>
	 */
	DYNAMIC_UPDATE;
	
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
	
	public static CreateMode defaultCreateMode() {
		return CREATE;
	}
}
