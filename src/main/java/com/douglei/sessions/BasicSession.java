package com.douglei.sessions;

/**
 * 
 * @author DougLei
 */
public interface BasicSession {
	/**
	 * 关闭session实例
	 */
	void close();
	
	void commit();
	void rollback();
}
