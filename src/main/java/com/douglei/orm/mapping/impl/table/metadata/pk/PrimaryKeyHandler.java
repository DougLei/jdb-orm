package com.douglei.orm.mapping.impl.table.metadata.pk;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author DougLei
 */
public interface PrimaryKeyHandler extends Serializable{

	/**
	 * 主键处理器是否支持处理多个主键列
	 * 默认为false
	 * @return
	 */
	default boolean supportProcessMultiPKColumns() {
		return true;
	}
	
	/**
	 * 给实体map设置主键值
	 * @param primaryKeyColumnCodes 当前操作的所有主键列code集合
	 * @param objectMap 当前操作的实体map
	 * @param originObject 当前操作的实体map的源实例, 在可以的情况下, 将生成的id值, 也要set到源实例的对应的主键属性中, 参考 @see UUID32PrimaryKeyHandler @see UUID36PrimaryKeyHandler
	 */
	void setValue2ObjectMap(Set<String> primaryKeyColumnCodes, Map<String, Object> objectMap, Object originObject);
}
