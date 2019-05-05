package com.douglei.sessions.session.query;

import java.util.List;

/**
 * 
 * @author DougLei
 */
public interface QuerySession {
	
	/**
	 * 查询数据集合
	 * @param query
	 * @return
	 */
	<T> List<T> query(Query query);
	
	/**
	 * 查询唯一数据
	 * @param query
	 * @return
	 */
	<T> T uniqueQuery(Query query);
}
