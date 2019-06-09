package com.douglei.orm.core.sql.pagequery;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分页查询用的sql语句对象
 * @author DougLei
 */
public class PageSqlStatement {
	private static final Logger logger = LoggerFactory.getLogger(PageSqlStatement.class);
	
	/**
	 * with子句
	 */
	private String withClause;
	/**
	 * 主sql语句
	 */
	private String sql;
	
	public PageSqlStatement(String originSql) {
		int withClauseEndIndex = withClauseEndIndex(originSql);
		if(withClauseEndIndex == -1) {
			withClause = ""; 
			sql = originSql;
		}else {
			withClause = originSql.substring(0, withClauseEndIndex);
			sql = originSql.substring(withClauseEndIndex);
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("完成 {} 实例化, originSql is: {}", PageSqlStatement.class.getName(), originSql);
			logger.debug("with clause sql is: {}", withClause);
			logger.debug("sql is: {}", sql);
		}
	}
	
	/**
	 * with子句的结束下标值
	 * @param originSql
	 * @return
	 */
	private int withClauseEndIndex(String originSql) {
		boolean includeWithStatement = originSql.trim().substring(0, 4).equalsIgnoreCase("with");
		if(includeWithStatement) {
			Stack<Character> parentheses = new Stack<Character>();
			parentheses.push('(');
			
			int index = originSql.indexOf("(")+1;
			int length = originSql.length();
			int i = index;
			char c;
			boolean isContinue = true;
			do{
				for(;i<length;i++) {
					c = originSql.charAt(i);
					if(c == '(') {
						parentheses.push(c);
					}else if(c == ')') {
						parentheses.pop();
						if(parentheses.isEmpty()) {
							index = i++;
							break;
						}
					}
				}
				
				for(;i<length;i++) {
					c = originSql.charAt(i);
					if(c == ' ' || c == '\r' || c == '\n' || c == '\t') {
						continue;
					}else {
						index = i++;
						if(c != ',') {
							isContinue = false;
						}
						break;
					}
				}
				
				if(i == length) {
					throw new WithClauseException("with子句语法异常, 请检查: " + originSql);
				}
			}while(isContinue);
			return index;
		}
		return -1;
	}

	public String getWithClause() {
		return withClause;
	}
	public String getSql() {
		return sql;
	}
}
