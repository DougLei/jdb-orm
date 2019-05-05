package com.douglei.sessions.session.table;

import com.douglei.sessions.session.table.impl.TableQuery;

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
