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
		pageQuerySql.append(" row_number() over(order by ").append(getOrderByInfo(statement)).append(") as rn, jdb_orm_second_query_.* from (");
		pageQuerySql.append(statement.getSql());
		pageQuerySql.append(") jdb_orm_second_query_) jdb_orm_thrid_query_ where jdb_orm_thrid_query_.rn >");
		pageQuerySql.append(maxIndex-pageSize);
		if(logger.isDebugEnabled()) {
			logger.debug("{} 进行分页查询的sql语句为: {}", getClass().getName(), pageQuerySql.toString());
		}
		return pageQuerySql.toString();
	}
	
	/**
	 * 获取最终order by的信息
	 * 1.从orderByInfo参数取
	 * 2.如果orderByInfo为null, 再尝试提取originSql语句中最后的一个order by子句的内容, 并将order by子句从originSql中移除
	 * 3.如果没有提取到, 则使用默认的order by值: current_timestamp
	 * 
	 * @param statement
	 * @return
	 */
	private String getOrderByInfo(PageSqlStatement statement) {
		if(statement.getOrderByInfo() != null) {
			return statement.getOrderByInfo();
		}
		
		
		
		return "current_timestamp";
	}
	
	
	
	
	public static void main(String[] args) {
		String sql = "select * from sys_user where xxx=xxx and xxx = xxx order by \n\t name desc, age \n asc \t ";
		System.out.println(sql);
		
		
		
	}
	
}
