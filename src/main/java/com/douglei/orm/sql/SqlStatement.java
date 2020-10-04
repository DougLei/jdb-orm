package com.douglei.orm.sql;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.dialect.db.sql.SqlStatementHandler;
import com.douglei.orm.sql.pagequery.PageSqlStatement;

/**
 * sql解析器
 * @author DougLei
 */
public class SqlStatement {
	private static final Logger logger = LoggerFactory.getLogger(PageSqlStatement.class);
	protected SqlStatementHandler sqlStatementHandler;
	
	/**
	 * with子句
	 */
	protected String withClause;
	/**
	 * 主sql语句
	 */
	protected String sql;
	/**
	 * order by子句
	 */
	protected String orderByClause;
	
	
	public SqlStatement(SqlStatementHandler sqlStatementHandler, String originSql) {
		this.sqlStatementHandler = sqlStatementHandler;
		resetSql(originSql);
	}
	
	/**
	 * 重置sql
	 * @param originSql
	 */
	public void resetSql(String originSql) {
		this.sql = originSql.trim();
		extractWithClause();
		extractOrderByClause();
		
		if(logger.isDebugEnabled()) {
			logger.debug("完成 {} 实例化, originSql is: {}", SqlStatement.class.getName(), originSql);
			logger.debug("with clause sql is: {}", withClause);
			logger.debug("sql is: {}", sql);
			logger.debug("orderByClause is: {}", orderByClause);
		}
	}
	
	/**
	 * 提取with子句
	 */
	private void extractWithClause() {
		int withClauseEndIndex = withClauseEndIndex();
		if(withClauseEndIndex > -1) {
			withClause = sql.substring(0, withClauseEndIndex);
			sql = sql.substring(withClauseEndIndex);
		}
	}
	
	/**
	 * 获取with子句的结束下标值, -1表示没有with子句
	 * @return
	 */
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
	
	/**
	 * 提取order by子句
	 */
	private void extractOrderByClause() {
		if(sqlStatementHandler.needExtractOrderByClause())
			new OrderByClauseResolver().extractOrderByClause(this);
	}
	
	/**
	 * 指定字符是否是空白
	 * @param c
	 * @return
	 */
	boolean isBlank(char c) {
		return c == ' ' || c == '\r' || c == '\n' || c == '\t';
	}
	
	/**
	 * 获取sql语句的总length
	 * @return
	 */
	public int length() {
		return (withClause==null?0:withClause.length()) + sql.length() + (orderByClause==null?0:orderByClause.length());
	}
	public String getWithClause() {
		return withClause;
	}
	public String getSql() {
		return sql;
	}
	public String getOrderByClause() {
		return orderByClause;
	}
	void setSql(String sql) {
		this.sql = sql;
	}
	void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}
}