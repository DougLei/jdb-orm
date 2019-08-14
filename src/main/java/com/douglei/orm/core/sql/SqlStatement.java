package com.douglei.orm.core.sql;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.core.sql.pagequery.PageSqlStatement;
import com.douglei.orm.core.sql.pagequery.WithClauseException;

/**
 * 
 * @author DougLei
 */
public class SqlStatement {
	private static final Logger logger = LoggerFactory.getLogger(PageSqlStatement.class);
	
	/**
	 * with子句
	 */
	private String withClause;
	/**
	 * 主sql语句
	 */
	private String sql;
	
	public SqlStatement(String originSql) {
		int withClauseEndIndex = withClauseEndIndex(originSql);
		if(withClauseEndIndex == -1) {
			withClause = ""; 
			sql = originSql;
		}else {
			withClause = originSql.substring(0, withClauseEndIndex);
			sql = originSql.substring(withClauseEndIndex);
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("完成 {} 实例化, originSql is: {}", SqlStatement.class.getName(), originSql);
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
					}else if(c == ')'){
						throw new WithClauseException("语法错误, with子句语的 [)] 不匹配, 请检查: " + originSql);
					}else {
						index = i++;
						if(c != ',') {
							isContinue = false;
						}
						break;
					}
				}
				
				if(i == length) {
					if(parentheses.isEmpty()) {
						throw new WithClauseException("语法错误, 只有with子句, 请检查: " + originSql);
					}
					throw new WithClauseException("语法错误, with子句语的 [(] 不匹配, 请检查: " + originSql); 
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
