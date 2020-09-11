package com.douglei.orm.sessionfactory.sessions.session.table;

import java.util.List;

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
	 * 保存对象, 同一个对象的集合
	 * @param objects
	 */
	void save(List<Object> objects);
	/**
	 * 保存对象
	 * @param code <table>元素中的name属性值, 或<table>元素中的class属性值
	 * @param object
	 */
	void save(String code, Object object);
	/**
	 * 保存对象
	 * @param code <table>元素中的name属性值, 或<table>元素中的class属性值
	 * @param objects
	 */
	void save(String code, List<Object> objects);
	
	/**
	 * 修改对象
	 * @param object
	 */
	default void update(Object object) {
		update(object, false);
	}
	/**
	 * 修改对象
	 * @param objects
	 */
	default void update(List<Object> objects) {
		update(objects, false);
	}
	/**
	 * 修改对象
	 * @param code <table>元素中的name属性值, 或<table>元素中的class属性值
	 * @param object
	 */
	default void update(String code, Object object) {
		update(code, object, false);
	}
	/**
	 * 修改对象
	 * @param code <table>元素中的name属性值, 或<table>元素中的class属性值
	 * @param objects
	 */
	default void update(String code, List<Object> objects) {
		update(code, objects, false);
	}
	
	/**
	 * 修改对象
	 * @param object
	 * @param updateNullValue 是否修改为null值, 即如果对象中属性的值为null, 是否将该字段的值也update为null
	 */
	void update(Object object, boolean updateNullValue);
	/**
	 * 修改对象
	 * @param objects
	 * @param updateNullValue 是否修改为null值, 即如果对象中属性的值为null, 是否将该字段的值也update为null
	 */
	void update(List<Object> objects, boolean updateNullValue);
	/**
	 * 修改对象
	 * @param code <table>元素中的name属性值, 或<table>元素中的class属性值
	 * @param object
	 * @param updateNullValue 是否修改为null值, 即如果对象中属性的值为null, 是否将该字段的值也update为null
	 */
	void update(String code, Object object, boolean updateNullValue);
	/**
	 * 修改对象
	 * @param code <table>元素中的name属性值, 或<table>元素中的class属性值
	 * @param objects
	 * @param updateNullValue 是否修改为null值, 即如果对象中属性的值为null, 是否将该字段的值也update为null
	 */
	void update(String code, List<Object> objects, boolean updateNullValue);
	
	
	/**
	 * 删除对象
	 * @param object
	 */
	void delete(Object object);
	/**
	 * 删除对象
	 * @param objects
	 */
	void delete(List<Object> objects);
	/**
	 * 删除对象
	 * @param code <table>元素中的name属性值, 或<table>元素中的class属性值
	 * @param object
	 */
	void delete(String code, Object object);
	/**
	 * 删除对象
	 * @param code <table>元素中的name属性值, 或<table>元素中的class属性值
	 * @param objects
	 */
	void delete(String code, List<Object> objects);
	
	/**
	 * 执行批量查询
	 * @param targetClass
	 * @param sql
	 * @return 
	 */
	<T> List<T> query(Class<T> targetClass, String sql);
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
	<T> T uniqueQuery(Class<T> targetClass, String sql);
	/**
	 * 查询唯一结果
	 * @param targetClass
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> T uniqueQuery(Class<T> targetClass, String sql, List<Object> parameters);
	
	/**
	 * 查询第一条数据
	 * @param targetClass
	 * @param sql
	 * @return 
	 */
	<T> T queryFirst(Class<T> targetClass, String sql);
	/**
	 * 查询第一条数据
	 * @param targetClass
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 
	 */
	<T> T queryFirst(Class<T> targetClass, String sql, List<Object> parameters);
	
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
	<T> PageResult<T> pageQuery(Class<T> targetClass, int pageNum, int pageSize, String sql, List<Object> parameters);
	
	/**
	 * 递归查询
	 * @param targetClass
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父主键的值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentPkColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称; 如果返回的是map, 则没有大小写要求(调用者自行决定); 如果需要返回指定class, 则写为对应class中存储子集合的属性名, 且该字集合的数据类型, 目前只支持为java.util.List
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> List<T> recursiveQuery(Class<T> targetClass, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql);
	/**
	 * 递归查询
	 * @param targetClass
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父主键的值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentPkColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称; 如果返回的是map, 则没有大小写要求(调用者自行决定); 如果需要返回指定class, 则写为对应class中存储子集合的属性名, 且该字集合的数据类型, 目前只支持为java.util.List
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> List<T> recursiveQuery(Class<T> targetClass, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql, List<Object> parameters);
	
	/**
	 * 分页递归查询, 只对根数据(即第一层的数据)进行分页
	 * @param targetClass
	 * @param pageNum
	 * @param pageSize
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父主键的值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentPkColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称; 如果返回的是map, 则没有大小写要求(调用者自行决定); 如果需要返回指定class, 则写为对应class中存储子集合的属性名, 且该字集合的数据类型, 目前只支持为java.util.List
	 * @param sql
	 * @return
	 */
	<T> PageResult<T> pageRecursiveQuery(Class<T> targetClass, int pageNum, int pageSize, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql);
	/**
	 * 分页递归查询, 只对根数据(即第一层的数据)进行分页
	 * @param targetClass
	 * @param pageNum
	 * @param pageSize
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父主键的值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentPkColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称; 如果返回的是map, 则没有大小写要求(调用者自行决定); 如果需要返回指定class, 则写为对应class中存储子集合的属性名, 且该字集合的数据类型, 目前只支持为java.util.List
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> PageResult<T> pageRecursiveQuery(Class<T> targetClass, int pageNum, int pageSize, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql, List<Object> parameters);
	
	/**
	 * 获取指定类的列名, 多个用, 分割
	 * @param clz
	 * @param excludeColumnNames 要排除的列名, 注意这里输入的是列名!
	 * @return
	 */
	default String getColumnNames(Class<?> clz, String... excludeColumnNames) {
		return getColumnNames(clz.getClass().getName(), excludeColumnNames);
	}
	
	/**
	 * 获取指定code的列名, 多个用, 分割
	 * @param code
	 * @param excludeColumnNames 要排除的列名, 注意这里输入的是列名!
	 * @return
	 */
	String getColumnNames(String code, String... excludeColumnNames);
}
