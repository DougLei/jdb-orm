package com.douglei.orm.sessionfactory.sessions.session.table;

import java.util.List;
import java.util.Map;

import com.douglei.orm.sessionfactory.sessions.sqlsession.PageRecursiveEntity;
import com.douglei.orm.sessionfactory.sessions.sqlsession.RecursiveEntity;
import com.douglei.orm.sql.query.page.PageResult;

/**
 * 和数据库交互的session接口
 * @author DougLei
 */
public interface TableSession {

	/**
	 * 保存对象
	 * <p>
	 * 在配置文件中配置了class时使用该方法
	 * @param object
	 */
	void save(Object object);
	/**
	 * 保存对象, 同一个对象的集合
	 * <p>
	 * 在配置文件中配置了class时使用该方法
	 * @param objects
	 */
	void save(List<? extends Object> objects);
	/**
	 * 保存对象
	 * <p>
	 * 在配置文件中没有配置class时使用该方法
	 * @param tableName
	 * @param objectMap
	 */
	void save(String tableName, Map<String, Object> objectMap);
	/**
	 * 保存对象
	 * <p>
	 * 在配置文件中没有配置class时使用该方法
	 * @param tableName
	 * @param objectMaps
	 */
	void save(String tableName, List<Map<String, Object>> objectMaps);
	
	
	/**
	 * 修改对象
	 * <p>
	 * 在配置文件中配置了class时使用该方法
	 * @param object
	 */
	default void update(Object object) {
		update(object, false);
	}
	/**
	 * 修改对象
	 * <p>
	 * 在配置文件中配置了class时使用该方法
	 * @param objects
	 */
	default void update(List<? extends Object> objects) {
		update(objects, false);
	}
	/**
	 * 修改对象
	 * <p>
	 * 在配置文件中没有配置class时使用该方法
	 * @param tableName
	 * @param objectMap
	 */
	default void update(String tableName, Map<String, Object> objectMap) {
		update(tableName, objectMap, false);
	}
	/**
	 * 修改对象
	 * <p>
	 * 在配置文件中没有配置class时使用该方法
	 * @param tableName
	 * @param objectMaps
	 */
	default void update(String tableName, List<Map<String, Object>> objectMaps) {
		update(tableName, objectMaps, false);
	}
	/**
	 * 修改对象
	 * <p>
	 * 在配置文件中配置了class时使用该方法
	 * @param object
	 * @param updateNullValue 是否修改为null值, 即如果对象中属性的值为null, 是否将该字段的值也update为null
	 */
	void update(Object object, boolean updateNullValue);
	/**
	 * 修改对象
	 * <p>
	 * 在配置文件中配置了class时使用该方法
	 * @param objects
	 * @param updateNullValue 是否修改为null值, 即如果对象中属性的值为null, 是否将该字段的值也update为null
	 */
	void update(List<? extends Object> objects, boolean updateNullValue);
	/**
	 * 修改对象
	 * <p>
	 * 在配置文件中没有配置class时使用该方法
	 * @param tableName
	 * @param objectMap
	 * @param updateNullValue 是否修改为null值, 即如果对象中属性的值为null, 是否将该字段的值也update为null
	 */
	void update(String tableName, Map<String, Object> objectMap, boolean updateNullValue);
	/**
	 * 修改对象
	 * <p>
	 * 在配置文件中没有配置class时使用该方法
	 * @param tableName
	 * @param objectMaps
	 * @param updateNullValue 是否修改为null值, 即如果对象中属性的值为null, 是否将该字段的值也update为null
	 */
	void update(String tableName, List<Map<String, Object>> objectMaps, boolean updateNullValue);
	
	
	/**
	 * 删除对象
	 * <p>
	 * 在配置文件中配置了class时使用该方法
	 * @param object
	 */
	void delete(Object object);
	/**
	 * 删除对象
	 * <p>
	 * 在配置文件中配置了class时使用该方法
	 * @param objects
	 */
	void delete(List<? extends Object> objects);
	/**
	 * 删除对象
	 * <p>
	 * 在配置文件中没有配置class时使用该方法
	 * @param tableName
	 * @param objectMap
	 */
	void delete(String tableName, Map<String, Object> objectMap);
	/**
	 * 删除对象
	 * <p>
	 * 在配置文件中没有配置class时使用该方法
	 * @param tableName
	 * @param objectMaps
	 */
	void delete(String tableName, List<Map<String, Object>> objectMaps);
	
	
	/**
	 * 执行批量查询
	 * @param clazz
	 * @param sql
	 * @return 
	 */
	<T> List<T> query(Class<T> clazz, String sql);
	/**
	 * 查询结果集
	 * @param clazz
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> List<T> query(Class<T> clazz, String sql, List<Object> parameters);
	
	/**
	 * 执行唯一查询
	 * @param clazz
	 * @param sql
	 * @return 
	 */
	<T> T uniqueQuery(Class<T> clazz, String sql);
	/**
	 * 查询唯一结果
	 * @param clazz
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> T uniqueQuery(Class<T> clazz, String sql, List<Object> parameters);
	
	/**
	 * 执行限制查询
	 * @param clazz
	 * @param startRow
	 * @param length
	 * @param sql
	 * @return 
	 */
	<T> List<T> limitQuery(Class<T> clazz, int startRow, int length, String sql);
	/**
	 * 执行限制查询
	 * @param clazz
	 * @param startRow 
	 * @param length 
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 
	 */
	<T> List<T> limitQuery(Class<T> clazz, int startRow, int length, String sql, List<Object> parameters);
	
	/**
	 * 分页查询
	 * @param clazz
	 * @param pageNum
	 * @param pageSize
	 * @param sql
	 * @return
	 */
	<T> PageResult<T> pageQuery(Class<T> clazz, int pageNum, int pageSize, String sql);
	
	/**
	 * 分页查询
	 * @param clazz
	 * @param pageNum
	 * @param pageSize
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> PageResult<T> pageQuery(Class<T> clazz, int pageNum, int pageSize, String sql, List<Object> parameters);
	
	/**
	 * 递归查询
	 * @param clazz
	 * @param parameter
	 * @param sql
	 * @return
	 */
	<T> List<T> recursiveQuery(Class<T> clazz, RecursiveEntity parameter, String sql);
	/**
	 * 递归查询
	 * @param clazz
	 * @param parameter
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> List<T> recursiveQuery(Class<T> clazz, RecursiveEntity parameter, String sql, List<Object> parameters);
	
	/**
	 * 分页递归查询
	 * @param clazz
	 * @param entity
	 * @param sql
	 * @return
	 */
	<T> PageResult<T> pageRecursiveQuery(Class<T> clazz, PageRecursiveEntity entity, String sql);
	/**
	 * 分页递归查询
	 * @param clazz
	 * @param entity
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> PageResult<T> pageRecursiveQuery(Class<T> clazz, PageRecursiveEntity entity, String sql, List<Object> parameters);
}
