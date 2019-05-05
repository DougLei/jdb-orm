package com.douglei.sessions.session;

import com.douglei.sessions.session.table.TableQuery;

/**
 * 
 * @author DougLei
 */
public interface QuerySession {
	
	/**
	 * 创建query实例
	 * @return
	 */
	TableQuery createTableQuery();
}
