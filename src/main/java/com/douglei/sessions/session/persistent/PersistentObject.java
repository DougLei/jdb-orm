package com.douglei.sessions.session.persistent;

/**
 * 持久化接口
 * @author DougLei
 */
public interface PersistentObject {
	
	/**
	 * 获取唯一编码值
	 * @return
	 */
	String getCode();
	
	/**
	 * 获取持久化对象id
	 * @return
	 */
	Identity getId();
}
