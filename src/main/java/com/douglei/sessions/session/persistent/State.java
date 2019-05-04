package com.douglei.sessions.session.persistent;

/**
 * 持久化对象的状态
 * @author DougLei
 */
public enum State {
	/**
	 * 新实例状态
	 * 即添加数据时的状态
	 */
	NEW_INSTANCE,
	/**
	 * 持久状态
	 */
	PERSISTENT;
}
