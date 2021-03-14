package com.douglei.orm.sessionfactory.sessions.sqlsession;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author DougLei
 */
@SuppressWarnings("rawtypes")
abstract class AbstractRecursiveQuerier<T extends RecursiveEntity> {
	protected Class clazz;
	protected T entity;
	
	protected List<Object> parameters; // 参数值集合
	protected int parametersInitialLength; // 参数值集合的初始长度
	
	/**
	 * 设置(第一次)递归条件sql
	 * @param sql
	 */
	protected final void setRecursiveConditionSQL(StringBuilder sql) {
		// 记录参数值集合的初始长度
		if(parameters != null)
			parametersInitialLength = parameters.size();
		
		// 开始拼接(第一次)递归条件sql
		sql.append(entity.getParentColumn());
		
		// 获取初始条件值数组
		Object[] values = entity.getValues();
		
		// 初始条件值为null时
		if(values == null) { 
			sql.append(" IS NULL");
		} 
		// 初始条件值只有一个时
		else if(values.length==1) { 
			sql.append("=?");
			addParameter(values[0]);
		} 
		// 初始条件值只有一个非null值时
		else if(values[1] == null) { 
			sql.append("=? OR ").append(entity.getParentColumn()).append(" IS NULL");
			addParameter(values[0]);
		} 
		// 初始条件值有多个时
		else { 
			sql.append(" IN (");
			int index = 0;
			for (Object value : values) {
				if(value == null)
					break;
				sql.append("?,");
				addParameter(value);
				index++;
			}
			sql.setLength(sql.length()-1);
			sql.append(')');
			
			if(index < values.length)
				sql.append(" OR ").append(entity.getParentColumn()).append(" IS NULL");
		}
	}
	
	// 添加参数值
	private void addParameter(Object value) {
		if(parameters == null)
			parameters = new ArrayList<Object>();
		parameters.add(value);
	}
}
