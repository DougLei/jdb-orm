package com.douglei.orm.sessionfactory.sessions.sqlsession;

import java.util.List;
import java.util.Map;

import com.douglei.orm.core.dialect.db.object.DBObjectType;
import com.douglei.orm.core.sql.pagequery.PageResult;

/**
 * 和数据库交互的sql session接口
 * @author DougLei
 */
public interface SqlSession {
	
	/**
	 * 执行批量查询
	 * @param sql
	 * @return 返回<列名:值>的list-map集合
	 */
	default List<Map<String, Object>> query(String sql) {
		return query(sql, null);
	}
	/**
	 * 执行批量查询
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 返回<列名:值>的list-map集合
	 */
	List<Map<String, Object>> query(String sql, List<Object> parameters);
	
	/**
	 * 执行批量查询
	 * @param targetClass
	 * @param sql
	 * @return 
	 */
	<T> List<T> query(Class<T> targetClass, String sql);
	/**
	 * 执行批量查询
	 * @param targetClass
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 
	 */
	<T> List<T> query(Class<T> targetClass, String sql, List<Object> parameters);
	
	/**
	 * 执行唯一查询
	 * @param sql
	 * @return 返回<列名:值>的map集合
	 */
	default Map<String, Object> uniqueQuery(String sql) {
		return uniqueQuery(sql, null);
	}
	/**
	 * 执行唯一查询
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 返回<列名:值>的map集合
	 */
	Map<String, Object> uniqueQuery(String sql, List<Object> parameters);
	
	/**
	 * 执行唯一查询
	 * @param targetClass
	 * @param sql
	 * @return 
	 */
	<T> T uniqueQuery(Class<T> targetClass, String sql);
	/**
	 * 执行唯一查询
	 * @param targetClass
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 
	 */
	<T> T uniqueQuery(Class<T> targetClass, String sql, List<Object> parameters);
	
	/**
	 * 执行批量查询
	 * @param sql
	 * @return 返回<值>的list-数组集合
	 */
	default List<Object[]> query_(String sql) {
		return query_(sql, null);
	}
	/**
	 * 执行批量查询
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 返回<值>的list-数组集合
	 */
	List<Object[]> query_(String sql, List<Object> parameters);
	
	/**
	 * 执行唯一查询
	 * @param sql
	 * @return 返回<值>的数组
	 */
	default Object[] uniqueQuery_(String sql) {
		return uniqueQuery_(sql, null);
	}
	/**
	 * 执行唯一查询
	 * @param sql
	 * @param parameters 传入的参数
	 * @return 返回<值>的数组
	 */
	Object[] uniqueQuery_(String sql, List<Object> parameters);
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param sql
	 * @return
	 */
	default PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String sql) {
		return pageQuery(pageNum, pageSize, sql, null);
	}
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param sql
	 * @param parameters
	 * @return
	 */
	PageResult<Map<String, Object>> pageQuery(int pageNum, int pageSize, String sql, List<Object> parameters);
	
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
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父id的(起始)值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称; 如果返回的是map, 则没有大小写要求(调用者自行决定); 如果需要返回指定class, 则写为对应class中存储子集合的属性名, 且该字集合的数据类型, 目前只支持为java.util.List
	 * @param sql
	 * @return
	 */
	default List<Map<String, Object>> recursiveQuery(int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql){
		return recursiveQuery(deep, pkColumnName, parentPkColumnName, parentValue, childNodeName, sql, null);
	}
	/**
	 * 递归查询
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父id的(起始)值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称; 如果返回的是map, 则没有大小写要求(调用者自行决定); 如果需要返回指定class, 则写为对应class中存储子集合的属性名, 且该字集合的数据类型, 目前只支持为java.util.List
	 * @param sql
	 * @param parameters
	 * @return
	 */
	List<Map<String, Object>> recursiveQuery(int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql, List<Object> parameters);
	
	/**
	 * 递归查询
	 * @param targetClass
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父id的(起始)值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentColumnName is null的数据
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
	 * @param parentValue 递归语句中, 父id的(起始)值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称; 如果返回的是map, 则没有大小写要求(调用者自行决定); 如果需要返回指定class, 则写为对应class中存储子集合的属性名, 且该字集合的数据类型, 目前只支持为java.util.List
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> List<T> recursiveQuery(Class<T> targetClass, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql, List<Object> parameters);
	
	/**
	 * 分页递归查询, 只对根数据(即第一层的数据)进行分页
	 * @param pageNum
	 * @param pageSize
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父id的(起始)值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称; 如果返回的是map, 则没有大小写要求(调用者自行决定); 如果需要返回指定class, 则写为对应class中存储子集合的属性名, 且该字集合的数据类型, 目前只支持为java.util.List
	 * @param sql
	 * @return
	 */
	default PageResult<Map<String, Object>> pageRecursiveQuery(int pageNum, int pageSize, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql){
		return pageRecursiveQuery(pageNum, pageSize, deep, pkColumnName, parentPkColumnName, parentValue, childNodeName, sql, null);
	}
	/**
	 * 分页递归查询, 只对根数据(即第一层的数据)进行分页
	 * @param pageNum
	 * @param pageSize
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父id的(起始)值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称; 如果返回的是map, 则没有大小写要求(调用者自行决定); 如果需要返回指定class, 则写为对应class中存储子集合的属性名, 且该字集合的数据类型, 目前只支持为java.util.List
	 * @param sql
	 * @param parameters
	 * @return
	 */
	PageResult<Map<String, Object>> pageRecursiveQuery(int pageNum, int pageSize, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql, List<Object> parameters);
	
	/**
	 * 分页递归查询, 只对根数据(即第一层的数据)进行分页
	 * @param targetClass
	 * @param pageNum
	 * @param pageSize
	 * @param deep 递归的深度, 小于等于0表示为无限递归
	 * @param pkColumnName 存储主键的列名
	 * @param parentPkColumnName 存储父级主键的列名
	 * @param parentValue 递归语句中, 父id的(起始)值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentColumnName is null的数据
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
	 * @param parentValue 递归语句中, 父id的(起始)值, 可以是单个值, 也可以是数组, 也可以是List, 如果传入null, 则表示查询parentColumnName is null的数据
	 * @param childNodeName 父级存储子集的节点名称; 如果返回的是map, 则没有大小写要求(调用者自行决定); 如果需要返回指定class, 则写为对应class中存储子集合的属性名, 且该字集合的数据类型, 目前只支持为java.util.List
	 * @param sql
	 * @param parameters
	 * @return
	 */
	<T> PageResult<T> pageRecursiveQuery(Class<T> targetClass, int pageNum, int pageSize, int deep, String pkColumnName, String parentPkColumnName, Object parentValue, String childNodeName, String sql, List<Object> parameters);
	
	/**
	 * 执行增删改查操作
	 * @param sql
	 * @return
	 */
	default int executeUpdate(String sql) {
		return executeUpdate(sql, null);
	}
	/**
	 * 执行增删改查操作
	 * @param sql
	 * @param parameters
	 * @return
	 */
	int executeUpdate(String sql, List<Object> parameters);
	
	/**
	 * 执行存储过程
	 * @param procedureExecutor
	 * @return
	 */
	Object executeProcedure(ProcedureExecutor procedureExecutor);
	
	/**
	 * 判断数据库对象是否存在
	 * @param dbObjectType
	 * @param dbObjectName
	 * @return
	 */
	boolean dbObjectExists(DBObjectType dbObjectType, String dbObjectName);
	
	/**
	 * 创建数据库对象, 如果存在则抛出已存在异常
	 * @param dbObjectType
	 * @param dbObjectName
	 * @param createSqlStatement
	 */
	void dbObjectCreate(DBObjectType dbObjectType, String dbObjectName, String createSqlStatement);
	
	/**
	 * 创建数据库对象
	 * @param dbObjectType
	 * @param dbObjectName
	 * @param createSqlStatement
	 * @param isOverride 如果对象已经存在, 是否覆盖; 如果存在且不覆盖, 则会抛出已存在异常
	 */
	void dbObjectCreate(DBObjectType dbObjectType, String dbObjectName, String createSqlStatement, boolean isOverride);
	
	/**
	 * 删除数据库对象
	 * @param dbObjectType
	 * @param dbObjectName
	 */
	void dbObjectDrop(DBObjectType dbObjectType, String dbObjectName);
}
