package com.douglei.orm.configuration.environment.mapping;

/**
 * sql映射中声明参数时的前后缀关系
 * @author DougLei
 */
public enum SqlMappingParameterPSRelation {
	
	/**
	 * 前后缀一样
	 */
	SAME,
	
	/**
	 * 前后缀不一样
	 */
	DIFFERENT,
	
	/**
	 * 前缀中包含后缀
	 */
	SUFFIX_IN_PREFIX,
	
	/**
	 * 后缀中包含前缀
	 */
	PREFIX_IN_SUFFIX;
}
