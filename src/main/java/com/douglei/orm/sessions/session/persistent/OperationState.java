package com.douglei.orm.sessions.session.persistent;

/**
 * 操作状态
 * @author DougLei
 */
public enum OperationState {
	CREATE,
	UPDATE,
	CREATE_DELETE,//标识create创建的数据, 又被删除
	DELETE;
}
