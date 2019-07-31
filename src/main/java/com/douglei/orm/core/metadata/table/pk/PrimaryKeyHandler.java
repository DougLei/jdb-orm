package com.douglei.orm.core.metadata.table.pk;

import java.util.Map;

import com.douglei.orm.core.metadata.table.ColumnMetadata;

/**
 * 
 * @author DougLei
 */
public abstract class PrimaryKeyHandler {
	
	/**
	 * 获取处理器名称
	 * @return
	 */
	public String getName() {
		return getClass().getName();
	}
	
	/**
	 * 主键处理器是否支持处理多个主键列
	 * 默认为false
	 * @return
	 */
	public boolean supportProcessMultiPKColumns() {
		return false;
	}
	
	/**
	 * 给实体map设置主键值
	 * @param code 当前操作的主键列的code
	 * @param column 当前操作的主键列
	 * @param entityMap 当前操作的实体map
	 * @return
	 */
	public abstract void setValue2EntityMap(String code, ColumnMetadata column, Map<String, Object> entityMap);
}
