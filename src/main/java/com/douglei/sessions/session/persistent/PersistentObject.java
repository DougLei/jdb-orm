package com.douglei.sessions.session.persistent;

import com.douglei.sessions.session.persistent.execution.ExecutionHolder;
import com.douglei.sessions.session.persistent.execution.ExecutionType;
import com.douglei.sessions.session.persistent.id.Identity;

/**
 * 持久化对象接口
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
	 * 获取源对象
	 * @return
	 */
	Object getOriginObject();
	/**
	 * set源对象
	 * @param originObject
	 */
	void setOriginObject(Object originObject);
	/**
	 * 获取类对象
	 * @return
	 */
	Object getClassObject();
	/**
	 * set类对象
	 * @param classObject
	 * @return
	 */
	void setClassObject(Object classObject);
	
	/**
	 * 获取执行对象
	 * @param executionType
	 * @return
	 */
	ExecutionHolder getExecutionHolder(ExecutionType executionType);
}
