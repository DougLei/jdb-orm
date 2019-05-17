package com.douglei.sessions.session.persistent.execution;

import java.util.List;

/**
 * 执行数据所有器
 * @author DougLei
 */
public interface ExecutionHolder {
	String getSql();
	List<Object> getParameters();
}
