package com.douglei.orm.dialect.datatype;

/**
 * 数据类型的分类
 * @author DougLei
 */
public enum Classification {
	
	/**
	 * 数据库的数据类型
	 */
	DB,
	
	/**
	 * 映射用的数据类型, 将某个数据类型, 映射到一个数据库的数据类型
	 */
	MAPPING;
}
