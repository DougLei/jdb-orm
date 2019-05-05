package com.douglei.sessions.session.table.impl.persistent;

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
	
	/**
	 * 获取持久化对象的状态
	 * @return
	 */
	State getState();
	/**
	 * 设置持久化对象的状态
	 * @param state
	 */
	void setState(State state);
	
	/**
	 * 获取执行对象
	 * @param executionType
	 * @return
	 */
	ExecutionHolder getExecutionHolder(ExecutionType executionType);
}
