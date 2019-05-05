package com.douglei.sessions;

/**
 * 
 * @author DougLei
 */
public interface BasicSession {
	
	/**
	 * 
	 */
	void commit();
	
	/**
	 * 
	 */
	void rollback();
	
	/**
	 * 
	 */
	void close();
}
