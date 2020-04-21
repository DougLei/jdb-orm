package com.douglei.orm.core.sql;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.core.sql.pagequery.PageSqlStatement;
import com.douglei.orm.core.sql.pagequery.WithClauseException;

/**
 * sql解析器
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
	protected String sql;
	
	public SqlStatement(String originSql) {
		resetSql(originSql);
	}
	
	/**
	 * 重置sql
	 * @param originSql
	 */
	public void resetSql(String originSql) {
		originSql = preProcessing(originSql.trim());
		int withClauseEndIndex = withClauseEndIndex(originSql);
		if(withClauseEndIndex == -1) {
			withClause = ""; 
			this.sql = originSql;
		}else {
			withClause = originSql.substring(0, withClauseEndIndex);
			this.sql = originSql.substring(withClauseEndIndex);
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("完成 {} 实例化, originSql is: {}", SqlStatement.class.getName(), originSql);
			logger.debug("with clause sql is: {}", withClause);
			logger.debug("sql is: {}", sql);
		}
	}
	
	/**
	 * 针对sql进行前置处理
	 * @param originSql
	 * @return
	 */
	protected String preProcessing(String originSql) {
		return originSql;
	}

	/**
	 * with子句的结束下标值
	 * @param originSql
	 * @return
	 */
	private int withClauseEndIndex(String originSql) {
		boolean includeWithStatement = originSql.substring(0, 4).equalsIgnoreCase("with");
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
							i++;
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
						if(c != ',') {
							index = i++;
							isContinue = false;
						}
						break;
					}
				}
				
				if(i == length) {
					if(parentheses.isEmpty()) {
						throw new WithClauseException("语法错误, 只有with子句, 请检查: " + originSql);
					}
					throw new WithClauseException("语法错误, with子句语的括号[(] 不匹配, 请检查: " + originSql); 
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
