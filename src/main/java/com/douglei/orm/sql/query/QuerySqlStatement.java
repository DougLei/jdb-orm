package com.douglei.orm.sql.query;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author DougLei
 */
public class QuerySqlStatement {
	private static final Logger logger = LoggerFactory.getLogger(QuerySqlStatement.class);
	protected String sql; // sql语句
	protected String withClause; // with子句
	protected String orderByClause; // order by子句
	
	/**
	 * 
	 * @param sql
	 * @param extractOrderByClause 是否需要提取sql中(最外层的)order by子句
	 */
	public QuerySqlStatement(String sql, boolean extractOrderByClause) {
		this.sql = sql.trim();
		extractWithClause();
		extractOrderByClause(extractOrderByClause);
		logger.debug("{}", this);
	}
	
	// 提取with子句
	private void extractWithClause() {
		int withClauseEndIndex = withClauseEndIndex();
		if(withClauseEndIndex > -1) {
			this.withClause = sql.substring(0, withClauseEndIndex);
			this.sql = sql.substring(withClauseEndIndex);
		}
	}
	
	// 获取with子句的结束下标值, -1表示没有with子句
	private int withClauseEndIndex() {
		if(sql.substring(0, 4).equalsIgnoreCase("with")) { // 判断是否包含with子句
			LinkedList<Character> parentheses = new LinkedList<Character>(); // 存储括号
			parentheses.add('(');
			
			int index = sql.indexOf("(")+1;
			int length = sql.length();
			int i = index;
			char c;
			boolean isContinue = true;
			do{
				for(;i<length;i++) {
					c = sql.charAt(i);
					if(c == '(') {
						parentheses.add(c);
					}else if(c == ')') {
						parentheses.removeLast();
						if(parentheses.isEmpty()) {
							i++;
							break;
						}
					}
				}
				
				for(;i<length;i++) {
					c = sql.charAt(i);
					if(isBlank(c)) {
						continue;
					}else if(c == ')'){
						throw new WithClauseException("语法错误, with子句的 [)] 不匹配, 请检查: " + sql);
					}else {
						if((c == 'a' || c == 'A') && (sql.charAt(i+1) == 's' || sql.charAt(i+1) == 'S')) { // 解决with查询中, 别名后用括号指定列名的sql, 例如 with qu(id, name) as (select ...)
							i+=2;
						}else if(c != ',') {
							index = i++;
							isContinue = false;
						}
						break;
					}
				}
				
				if(i == length) {
					if(parentheses.isEmpty()) {
						throw new WithClauseException("语法错误, 只有with子句, 请检查: " + sql);
					}
					throw new WithClauseException("语法错误, with子句语的括号[(] 不匹配, 请检查: " + sql); 
				}
			}while(isContinue);
			return index;
		}
		return -1;
	}
	
	// 提取sql中(最外层的)order by子句
	private void extractOrderByClause(boolean extractOrderByClause) {
		if(extractOrderByClause)
			new OrderByClauseParser().extract(this);
	}
	
	/**
	 * 指定字符是否是空白
	 * @param c
	 * @return
	 */
	protected boolean isBlank(char c) {
		return c == ' ' || c == '\r' || c == '\n' || c == '\t';
	}
	
	/**
	 * 获取sql语句
	 * @return
	 */
	public String getSql() {
		return sql;
	}
	/**
	 * 获取with子句
	 * @return
	 */
	public String getWithClause() {
		return withClause;
	}
	/**
	 * 获取order by子句
	 * @return
	 */
	public String getOrderByClause() {
		return orderByClause;
	}
	/**
	 * 获取sql语句的总长度
	 * @return
	 */
	public int getTotalLength() {
		return (withClause==null?0:withClause.length()) + sql.length() + (orderByClause==null?0:orderByClause.length());
	}

	@Override
	public String toString() {
		return "[sql=" + sql + ", withClause=" + withClause + ", orderByClause=" + orderByClause + "]";
	}
}