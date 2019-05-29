package com.douglei.database.dialect.db.procedure;

/**
 * 
 * @author DougLei
 */
public interface ProcedureHandler {
	
	/**
	 * <pre>
	 * 	存储过程支持直接返回 ResultSet
	 * 	即存储过程中编写 select语句, 执行该存储过程后, 会展示出该select结果集, 例如sqlserver数据库, mysql数据库
	 * 	像oracle数据库, 是必须通过输出参数才能返回结果集(cursor类型)
	 * </pre>
	 * @return
	 */
	boolean supportDirectlyReturnResultSet();
}
