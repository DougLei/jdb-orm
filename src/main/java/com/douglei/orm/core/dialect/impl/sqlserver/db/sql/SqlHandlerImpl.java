package com.douglei.orm.core.dialect.impl.sqlserver.db.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.core.dialect.db.sql.SqlHandler;
import com.douglei.orm.core.sql.pagequery.PageSqlStatement;

/**
 * 
 * @author DougLei
 */
public class SqlHandlerImpl implements SqlHandler{
	private static final Logger logger = LoggerFactory.getLogger(SqlHandlerImpl.class);
	
	@Override
	public String installPageQuerySql(int pageNum, int pageSize, PageSqlStatement statement) {
		int maxIndex = pageNum*pageSize;
		
		StringBuilder pageQuerySql = new StringBuilder(240 + statement.getWithClause().length() + statement.getSql().length());
		pageQuerySql.append(statement.getWithClause());
		pageQuerySql.append(" select jdb_orm_thrid_query_.* from (select top ");
		pageQuerySql.append(maxIndex);
		pageQuerySql.append(" row_number() over(order by ").append(updatePageSqlStatement(statement).getOrderBySql()).append(") as rn, jdb_orm_second_query_.* from (");
		pageQuerySql.append(statement.getSql());
		pageQuerySql.append(") jdb_orm_second_query_) jdb_orm_thrid_query_ where jdb_orm_thrid_query_.rn >");
		pageQuerySql.append(maxIndex-pageSize);
		if(logger.isDebugEnabled()) {
			logger.debug("{} 进行分页查询的sql语句为: {}", getClass().getName(), pageQuerySql.toString());
		}
		return pageQuerySql.toString();
	}
	
	/**
	 * 更新分页查询对象
	 * 1.从orderByInfo参数取
	 * 2.如果orderByInfo为null, 再尝试提取originSql语句中最后的一个order by子句的内容, 并将order by子句从originSql中移除
	 * 3.如果没有提取到, 则使用默认的order by值: current_timestamp
	 * @param statement
	 * @return
	 */
	private PageSqlStatement updatePageSqlStatement(PageSqlStatement statement) {
		if(!extractOrderByInfo(statement))
			statement.setOrderBySql("current_timestamp");
		return statement;
	}
	
	/**
	 * 从分页查询对象中提取出order by信息
	 * @param statement
	 * @return 提取是否成功
	 */
	private boolean extractOrderByInfo(PageSqlStatement statement) {
		// TODO Auto-generated method stub
		
		
		// 从sql最后向前找, 每找到一个单词, 判断是否是select语句中的关键字, 这里的关键字主要是排在order by前的, 例如having, gourp by, where, on, join, from,
		// 如果在解析出这些关键字的时候, 还未出现order by, 则证明没有order by子句, 停止解析
		// 解析过程中, 如果
		
		
		
		
		
		
		return false;
	}
	
	// 指定字符是否是英文字母
	private static boolean isEnglishLetters(char c) {
		return c >= 97 && c <=122 || c >= 65 && c <= 90;
	}
	
	
	public static void main(String[] args) {
		String sql = "select * from sys_user where xxx=xxx and xxx = xxx ORDER by \n\t name desc, age \n asc \t ";
		
		
		int charStartIndex, charEndIndex;
		char c;
		int index = sql.length()-1;
		for(;index > -1;index--) {
			if(isEnglishLetters(c = sql.charAt(index))) {
				
			}else {
				
			}
		}
	}
}
