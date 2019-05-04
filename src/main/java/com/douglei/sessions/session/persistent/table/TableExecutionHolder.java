package com.douglei.sessions.session.persistent.table;

import java.util.List;

import com.douglei.sessions.session.persistent.ExecutionHolder;
import com.douglei.sessions.session.persistent.ExecutionType;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class TableExecutionHolder implements ExecutionHolder{
	private String sql;
	private List<Object> parameters;
	
	public TableExecutionHolder(ExecutionType executionType, String sql, List<Object> parameters) {
		if(StringUtil.isEmpty(sql)) {
			throw new NullPointerException("要执行的sql语句不能为空");
		}
		this.sql = sql;
		this.parameters = parameters;
	}

	@Override
	public String getSql() {
		return sql;
	}

	@Override
	public List<Object> getParameters() {
		return parameters;
	}
}
