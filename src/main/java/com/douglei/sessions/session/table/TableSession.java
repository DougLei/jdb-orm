package com.douglei.sessions.session.table;

import java.util.List;
import java.util.Map;

import com.douglei.database.sql.pagequery.PageResult;
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
	 * 查询结果集
	 * @param targetClass
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> List<T> query(Class<T> targetClass, String sql, List<? extends Object> parameters);
	
	/**
	 * 查询唯一结果
	 * @param targetClass
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> T uniqueQuery(Class<T> targetClass, String sql, List<? extends Object> parameters);
	
	/**
	 * 分页查询
	 * @param targetClass
	 * @param pageNum
	 * @param pageSize
	 * @param sql
	 * @return
	 */
	<T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String sql);
	
	/**
	 * 分页查询
	 * @param targetClass
	 * @param pageNum
	 * @param pageSize
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String sql, List<? extends Object> parameters);
}
