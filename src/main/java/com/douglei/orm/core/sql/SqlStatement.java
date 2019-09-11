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
	private String sql;
	/**
	 * 最终order by的子句的声明, 使用方式如下
	 * {name desc, age ...} select * from user ....
	 * 即使用{}包裹住select语句最终要用来排序的字段信息, 不用写order by关键字
	 * 注意, sql语句还是该怎么写就怎么写, 这里只是多了一个需要声明的地方
	 */
	private String finalOrderByClauseStatement;
	
	public SqlStatement() {
	}
	public SqlStatement(String originSql) {
		setSql(originSql);
	}
	
	public void setSql(String originSql) {
		int withClauseEndIndex = withClauseEndIndex(originSql);
		if(withClauseEndIndex == -1) {
			withClause = ""; 
			resolveSql(originSql);
		}else {
			withClause = originSql.substring(0, withClauseEndIndex);
			resolveSql(originSql.substring(withClauseEndIndex));
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
					throw new WithClauseException("语法错误, with子句语的 [(] 不匹配, 请检查: " + originSql); 
				}
			}while(isContinue);
			return index;
		}
		return -1;
	}
	
	/**
	 * 解析sql语句
	 * 如果语句开头为{, 则证明里面有声明的order by信息, 提取出来, 将提取后的sql语句set给sql属性, 提取出来的order by数据, set给finalOrderByClauseStatement属性
	 * 否则直接将sql语句set给sql属性
	 * @param sql
	 */
	private void resolveSql(String sql) {
		if(sql.charAt(0) == '{') {
			int i=1, flag = sql.length();
			for(;i<flag;i++) {
				if(sql.charAt(i) == '}') {
					flag = i;
					break;
				}
			}
			this.finalOrderByClauseStatement = sql.substring(1, flag);
			this.sql = sql.substring(flag+1);
		}else {
			this.sql = sql;
		}
	}

	public String getWithClause() {
		return withClause;
	}
	public String getFinalOrderByClauseStatement() {
		return finalOrderByClauseStatement;
	}
	public String getSql() {
		return sql;
	}
}
