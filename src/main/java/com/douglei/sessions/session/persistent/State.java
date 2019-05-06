package com.douglei.sessions.session.persistent;

/**
 * 
 * @author DougLei
 */
public enum State {
	/**
	 * 新实例状态
	 * 即添加新数据时的状态
	 */
	NEW_INSTANCE,
	/**
	 * 持久状态
	 * 即和数据库有关联时的状态
	 */
	PERSISTENT;
}
