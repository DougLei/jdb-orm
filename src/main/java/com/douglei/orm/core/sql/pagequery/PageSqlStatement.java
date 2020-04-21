package com.douglei.orm.core.sql.pagequery;

import com.douglei.orm.core.sql.SqlStatement;

/**
 * 分页查询用的sql语句对象
 * @author DougLei
 */
public class PageSqlStatement extends SqlStatement{
	
	/**
	 * 该参数针对sqlserver数据库时使用
	 * 因为sqlserver分页使用到了 row_number() over() 函数, 在over中需要传入排序的字段
	 * 这里的设计是在sql的最前面, 加上需要排序的字段, 如下
	 * {name desc, age ...(排序)} + with子句 + select语句
	 * 程序会将最前面排序的信息解析出来, 放到over()函数中
	 * 
	 * 这个设计是临时的, 后续提取order by的方法完善后, 该设计会被删除 2020.4.21
	 */
	private String orderByInfo;
	
	public PageSqlStatement(String originSql) {
		super(originSql);
	}
	
	/**
	 * 修改sql语句
	 * @param sql
	 */
	public void updateSql(String sql) {
		super.sql = sql;
	}
	
	/**
	 * 针对sqlserver数据库, 从传来的sql语句前, 解析出排序信息(如果有的化)
	 */
	@Override
	protected String preProcessing(String originSql) {
		if(originSql.charAt(0) == '{') {
			short i=1, flag = (short)originSql.length();
			for(;i<flag;i++) {
				if(originSql.charAt(i) == '}') {
					flag = i;
					break;
				}
			}
			this.orderByInfo = originSql.substring(1, flag);
			originSql = originSql.substring(flag+1).trim();
		}
		return originSql;
	}
	
	public String getOrderByInfo() {
		return orderByInfo;
	}
	public void setOrderByInfo(String orderByInfo) {
		this.orderByInfo = orderByInfo;
	}
}
