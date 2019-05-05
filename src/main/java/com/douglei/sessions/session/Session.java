package com.douglei.sessions.session;

import java.util.Map;

import com.douglei.sessions.BasicSession;

/**
 * 和数据库交互的session接口
 * @author DougLei
 */
public interface Session extends BasicSession{

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
}