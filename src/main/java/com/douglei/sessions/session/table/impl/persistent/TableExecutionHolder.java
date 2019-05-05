package com.douglei.sessions.session.table.impl.persistent;

import java.util.List;

import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class TableExecutionHolder implements ExecutionHolder{
	private String sql;
	private List<? extends Object> parameters;
	
	public TableExecutionHolder(String sql, List<? extends Object> parameters) {
		if(StringUtil.isEmpty(sql)) {
			throw new NullPointerException("要执行的sql语句不能为空");
		}
		this.sql = sql;
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return (StringUtil.isEmpty(sql)?"sql is null":sql) + " -- " + ((parameters==null || parameters.size()==0)?"parameters is null":parameters.toString());
	}
	
	@Override
	public String getSql() {
		return sql;
	}

	@Override
	public List<? extends Object> getParameters() {
		return parameters;
	}
}
