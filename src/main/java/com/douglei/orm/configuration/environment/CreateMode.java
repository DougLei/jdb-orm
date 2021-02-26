package com.douglei.orm.configuration.environment;

import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public enum CreateMode {
	
	/**
	 * 不处理模式
	 */
	NONE,
	/**
	 * 创建模式; 如果没有就创建, 有就不处理
	 */
	CREATE,
	/**
	 * 删除再创建模式; 如果存在就先删除, 再创建, 否则就直接创建; 适合用于重置表
	 */
	DROP_CREATE,
	/**
	 * 动态模式; 会根据映射结构的变化, 去动态更新数据库对象的结构
	 */
	DYNAMIC;
	
	/**
	 * 根据字符串值, 获取对应的创建模式; 没有匹配时会返回null
	 * @param value
	 * @return
	 */
	public static CreateMode toValue(String value) {
		if(StringUtil.isEmpty(value))
			return null;
		
		value = value.toUpperCase();
		for (CreateMode cm : CreateMode.values()) {
			if(cm.name().equals(value)) 
				return cm;
		}
		return null;
	}
}
