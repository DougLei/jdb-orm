package com.douglei.sessions.session.table;

import java.util.Map;

import com.douglei.sessions.BasicSession;

/**
 * 和数据库交互的session接口
 * @author DougLei
 */
public interface TableSession extends BasicSession{

	/**
	 * 保存对象
	 * @param object
	 */
	void save(Object object);
	/**
	 * 保存对象
	 * @param code <table>元素中的name属性值
	 * @param propertyMap
	 */
	void save(String code, Map<String, Object> propertyMap);
	
	/**
	 * 修改对象
	 * @param object
	 */
	void update(Object object);
	/**
	 * 修改对象
	 * @param code <table>元素中的name属性值
	 * @param propertyMap
	 */
	void update(String code, Map<String, Object> propertyMap);
	
	/**
	 * 删除对象
	 * @param object
	 */
	void delete(Object object);
	/**
	 * 删除对象
	 * @param code <table>元素中的name属性值
	 * @param propertyMap
	 */
	void delete(String code, Map<String, Object> propertyMap);
	
	/**
	 * 根据id, 查询对应的数据
	 * @param targetClass
	 * @param id 基础类型值 或 Map<String, 基础类型值>
	 * @return
	 */
	<T> T queryById(Class<T> targetClass, Object id);
	
	/**
	 * 根据id, 查询对应的数据
	 * @param targetClass
	 * @param code
	 * @param id id 基础类型值 或 Map<String, 基础类型值>
	 * @return
	 */
	<T> T queryById(Class<T> targetClass, String code, Object id);
}
