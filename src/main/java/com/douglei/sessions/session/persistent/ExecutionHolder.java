package com.douglei.sessions.session.persistent;

import java.util.List;

/**
 * 
 * @author DougLei
 */
public interface ExecutionHolder {
	String getSql();
	List<Object> getParameters();
}
