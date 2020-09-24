package com.douglei.orm.core.metadata;

import com.douglei.tools.utils.StringUtil;

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
	 */
	DYNAMIC_UPDATE;
	
	/**
	 * 根据字符串值, 获取对应的创建模式
	 * @param value
	 * @return
	 */
	public static CreateMode toValue(String value) {
		if(StringUtil.notEmpty(value)) {
			value = value.toUpperCase();
			CreateMode[] cms = CreateMode.values();
			for (CreateMode cm : cms) {
				if(cm.name().equals(value)) {
					return cm;
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取默认的创建模式
	 * @return
	 */
	public static CreateMode defaultCreateMode() {
		return CREATE;
	}
}