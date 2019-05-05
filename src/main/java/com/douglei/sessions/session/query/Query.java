package com.douglei.sessions.session.query;

/**
 * 
 * @author DougLei
 */
public interface Query {

	/**
	 * 查询指定类映射的表
	 * @param clz
	 * @return
	 */
	Query from(Class<?> clz);
}
