package com.douglei.sessions.session.persistent.execution;

import java.util.List;

/**
 * 
 * @author DougLei
 */
public interface ExecutionHolder {
	String getSql();
	List<? extends Object> getParameters();
}
