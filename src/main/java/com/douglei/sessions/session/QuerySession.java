package com.douglei.sessions.session;

import java.util.List;

import com.douglei.sessions.session.query.Query;

/**
 * 
 * @author DougLei
 */
public interface QuerySession {
	
	/**
	 * 创建query实例
	 * @return
	 */
	Query createQuery();
	
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
