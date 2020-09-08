package com.douglei.orm.core.sql.statement.entity;

/**
 * 列名转换器, 针对查询结果为Map时的列名处理
 * @author DougLei
 */
public class ColumnNameConverter {
	
	/**
	 * 进行转换, 默认实现为转大写
	 * @param columnName
	 * @return
	 */
	public String convert(String columnName) {
		return columnName.toUpperCase();
	}
}
