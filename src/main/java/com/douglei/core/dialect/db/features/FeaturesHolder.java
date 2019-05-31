package com.douglei.core.dialect.db.features;

/**
 * 数据库特性持有者
 * @author DougLei
 */
public interface FeaturesHolder {
	
	/**
	 * <pre>
	 * 	存储过程支持直接返回 ResultSet
	 * 	即存储过程中编写 select语句, 执行该存储过程后, 会展示出该select结果集, 例如sqlserver数据库, mysql数据库
	 * 	像oracle数据库, 是必须通过输出参数才能返回结果集(cursor类型)
	 * </pre>
	 * @return
	 */
	boolean procedureSupportDirectlyReturnResultSet();
	
	// TODO 各个数据库再删除列的时候，会不会对相应的约束进行处理
}
