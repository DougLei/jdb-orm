package com.douglei.orm.context;

import com.douglei.orm.configuration.environment.mapping.MappingType;

/**
 * 执行映射时的描述上下文
 * @author DougLei
 */
public class ExecMappingDescriptionContext {
	private static final ThreadLocal<String> mappingDescription = new ThreadLocal<String>();
	
	/**
	 * 记录执行的映射描述
	 * @param code
	 * @param type
	 */
	public static void setExecMappingDescription(String code, MappingType type) {
		mappingDescription.set("执行code=["+code+"]的["+type.name()+"]映射");
	}
	
	/**
	 * 获取执行的映射描述
	 */
	public static String getExecMappingDescription() {
		return mappingDescription.get();
	}
}
