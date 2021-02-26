package com.douglei.orm.mapping.impl.table.pk;

import java.util.Map;
import java.util.Set;

/**
 * 
 * @author DougLei
 */
public interface PrimaryKeyHandler {
	
	/**
	 * 获取主键处理器的类型
	 * @return
	 */
	default String getType() {
		return getClass().getName();
	}
	
	/**
	 * 是否支持处理联合主键
	 * @return
	 */
	default boolean supportCompositeKeys() {
		return false;
	}
	
	/**
	 * 给实体map设置主键值
	 * @param primaryKeyColumnCodes 当前操作的所有主键列code集合
	 * @param objectMap 当前操作的实体map
	 * @param originObject 当前操作的实体map的源实例, 在可以的情况下, 将生成的id值, 也要set到源实例的对应的主键属性中, 参考 @see UUID32PrimaryKeyHandler @see UUID36PrimaryKeyHandler
	 */
	void setValue2ObjectMap(Set<String> primaryKeyColumnCodes, Map<String, Object> objectMap, Object originObject);
}
