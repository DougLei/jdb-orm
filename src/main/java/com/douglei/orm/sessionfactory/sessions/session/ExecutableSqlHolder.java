package com.douglei.orm.sessionfactory.sessions.session;

import java.util.List;

/**
 * 可执行sql的持有器
 * @author DougLei
 */
public interface ExecutableSqlHolder {
	
	/**
	 * 可执行的sql语句的数量
	 * @return
	 */
	default int executableSqlCount() {
		return 1;
	}
	
	/**
	 * 移动到下一个sql语句和parameters
	 * @return 如果next后还有要执行的sql, 返回true, 否则返回false
	 */
	default boolean next() {
		return false;
	}
	
	/**
	 * 获取当前要执行的sql语句
	 * @return
	 */
	String getCurrentSql();
	
	/**
	 * 获取当前执行sql语句对应的参数值集合
	 * @return
	 */
	List<Object> getCurrentParameterValues();
}
