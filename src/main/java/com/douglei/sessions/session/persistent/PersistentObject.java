package com.douglei.sessions.session.persistent;

import com.douglei.sessions.session.persistent.execution.ExecutionHolder;
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
	void setIdentity(Identity identity);
	
	/**
	 * 获取持久化对象的状态
	 * @return
	 */
	OperationState getOperationState();
	/**
	 * 设置持久化对象的状态
	 * @param operationState
	 */
	void setOperationState(OperationState operationState);
	
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
	 * 获取执行对象
	 * @return
	 */
	ExecutionHolder getExecutionHolder();
}
