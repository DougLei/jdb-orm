package com.douglei.orm.sessionfactory.sessions.session.execute;

import java.util.List;

/**
 * 
 * @author DougLei
 */
public interface ExecuteHandler {
	
	/**
	 * 要执行的sql语句的数量
	 * @return
	 */
	short executeSqlCount();
	
	/**
	 * 移动到下一个sql语句和parameters
	 * @return 如果next后还有要执行的sql, 返回true, 否则返回false
	 */
	boolean next();
	
	/**
	 * 获取当前要执行的sql语句
	 * @return
	 */
	String getCurrentSql();
	
	/**
	 * 获取当前执行sql语句对应的参数集合
	 * @return
	 */
	List<Object> getCurrentParameters();
}
