package com.douglei.orm.sessionfactory.sessions.session.sqlquery.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.douglei.orm.mapping.impl.sqlquery.metadata.SqlMetadata;
import com.douglei.orm.sessionfactory.sessions.session.IExecutableSql;

/**
 * 
 * @author DougLei
 */
public class ExecutableQuerySql implements IExecutableSql{
	private SqlMetadata sqlMetadata; // 原sql
	private HashSet<String> conditionSQLMark; // 组装条件sql时, 记录每个条件的参数名(去重), 用来在最后验证是否没有传入必要的参数
	private StringBuilder conditionSQL; // 条件sql
	private List<Object> parameterValues; // 执行sql语句需要的参数值集合, 可为null
	private StringBuilder resultSQL; // (查询)结果sql
	private StringBuilder orderbySQL; // 排序sql

	ExecutableQuerySql(SqlMetadata sqlMetadata) {
		this.sqlMetadata = sqlMetadata;
	}
	
	// 获取条件sql
	private StringBuilder getConditionSQL() {
		if(conditionSQLMark == null) {
			conditionSQLMark = new HashSet<String>();
			conditionSQL = new StringBuilder(300);
			conditionSQL.append(" WHERE ");
			parameterValues = new ArrayList<Object>();
		}
		return conditionSQL;
	}
	
	/**
	 * 追加条件SQL
	 * @param sql
	 * @param name
	 * @param values
	 */
	public void appendConditionSQL(String sql, String name, Object... values) {
		getConditionSQL().append(sql); // 追加sql
		conditionSQLMark.add(name); // 记录参数
		if(values.length > 0) { // 记录参数值
			for (Object value : values) { 
				if(value != null)
					parameterValues.add(value);
			}
		}
	}
	
	/**
	 * 追加条件SQL的左括号
	 */
	public void appendConditionSQLLeftParenthesis() {
		getConditionSQL().append('(');
	}
	/**
	 * 追加条件SQL的右括号
	 */
	public void appendConditionSQLRightParenthesis() {
		conditionSQL.append(')');
	}
	
	/**
	 * 追加条件SQL的逻辑运算符
	 * @param next
	 */
	public void appendConditionSQLNext(LogicalOperator next) {
		conditionSQL.append(' ').append(next.name()).append(' ');
	}
	
	/**
	 * 条件sql中, 是否存在指定name的参数
	 * @param name
	 * @return
	 */
	public boolean existsInConditionSQL(String name) {
		if(conditionSQLMark == null)
			return false;
		return conditionSQLMark.contains(name);
	}
	
	/**
	 * 追加(查询)结果SQL
	 * @param name
	 * @param alias
	 */
	public void appendResultSQL(String name, String alias) {
		if(resultSQL == null) {
			resultSQL = new StringBuilder(100);
			resultSQL.append("SELECT ");
		}else {
			resultSQL.append(',');
		}
		resultSQL.append(name);
		if(alias != null)
			resultSQL.append(' ').append(alias);
	}
	
	/**
	 * 追加排序SQL
	 * @param name
	 * @param order
	 */
	public void appendOrderbySQL(String name, String order) {
		if(orderbySQL == null) {
			orderbySQL = new StringBuilder(100);
			orderbySQL.append(" ORDER BY ");
		}else {
			orderbySQL.append(',');
		}
		orderbySQL.append(name);
		if(order != null)
			orderbySQL.append(' ').append(order);
	}
	
	@Override
	public String getCurrentSql() {
		StringBuilder sql = new StringBuilder(sqlMetadata.getTotalLength() + 500);
		
		// append with子句
		if(sqlMetadata.getWithClause() != null)
			resultSQL.append(sqlMetadata.getWithClause()).append(' ');
		
		// append(查询)结果sql
		if(resultSQL == null) {
			sql.append("SELECT * FROM (");
		}else {
			sql.append(resultSQL).append(" FROM (");
		}
		
		// append原sql
		sql.append(sqlMetadata.getSql()).append(") _SUB_SQ_");
		
		// append条件sql
		if(conditionSQL != null)
			sql.append(conditionSQL);
		
		// append排序sql
		if(orderbySQL != null)
			sql.append(orderbySQL);
		
		return sql.toString();
	}
	
	@Override
	public List<Object> getCurrentParameterValues() {
		return parameterValues;
	}

	@Override
	public String toString() {
		return "\nsqlMetadata=" + sqlMetadata + "\nconditionSQL=" + conditionSQL + "\norderbySQL=" + orderbySQL + "\nparameterValues=" + parameterValues;
	}
}
