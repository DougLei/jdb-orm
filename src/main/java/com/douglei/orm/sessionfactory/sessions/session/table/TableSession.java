package com.douglei.orm.sessionfactory.sessions.session.table;

import java.util.List;
import java.util.Map;

import com.douglei.orm.core.sql.pagequery.PageResult;

/**
 * 和数据库交互的session接口
 * @author DougLei
 */
public interface TableSession {

	/**
	 * 保存对象
	 * @param object
	 */
	void save(Object object);
	/**
	 * 保存对象
	 * @param code <table>元素中的name属性值, 或<table>元素中的class属性值
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
	 * @param code <table>元素中的name属性值, 或<table>元素中的class属性值
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
	 * @param code <table>元素中的name属性值, 或<table>元素中的class属性值
	 * @param propertyMap
	 */
	void delete(String code, Map<String, Object> propertyMap);
	
	/**
	 * 执行批量查询
	 * @param targetClass
	 * @param sql
	 * @return 
	 */
	default <T> List<T> query(Class<T> targetClass, String sql) {
		return query(targetClass, sql, null);
	}
	/**
	 * 查询结果集
	 * @param targetClass
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> List<T> query(Class<T> targetClass, String sql, List<Object> parameters);
	
	/**
	 * 执行唯一查询
	 * @param targetClass
	 * @param sql
	 * @return 
	 */
	default <T> T uniqueQuery(Class<T> targetClass, String sql) {
		return uniqueQuery(targetClass, sql, null);
	}
	
	/**
	 * 查询唯一结果
	 * @param targetClass
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> T uniqueQuery(Class<T> targetClass, String sql, List<Object> parameters);
	
	/**
	 * 分页查询
	 * @param targetClass
	 * @param pageNum
	 * @param pageSize
	 * @param sql
	 * @return
	 */
	default <T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String sql) {
		return pageQuery(targetClass, pageNum, pageSize, sql, null);
	}
	
	/**
	 * 分页查询
	 * @param targetClass
	 * @param pageNum
	 * @param pageSize
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String sql, List<Object> parameters);
	
	/**
	 * 获取指定类的列名, 多个用, 分割
	 * @param clz
	 * @param excludeColumnNames 要排除的列名
	 * @return
	 */
	default String getColumnNames(Class<?> clz, String... excludeColumnNames) {
		return getColumnNames(clz.getClass().getName(), excludeColumnNames);
	}
	
	/**
	 * 获取指定code的列名, 多个用, 分割
	 * @param code
	 * @param excludeColumnNames 要排除的列名
	 * @return
	 */
	String getColumnNames(String code, String... excludeColumnNames);
}
