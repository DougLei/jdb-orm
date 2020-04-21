package com.douglei.orm.core.dialect.impl.sqlserver.db.sql;

import com.douglei.orm.core.sql.pagequery.PageSqlStatement;

/**
 * order by解析器
 * @author DougLei
 */
class OrderBySqlResolver {
	// 记录解析出的每个单词的起始下标, 结束下标, 以及长度
	private int wordStartIndex;
	private int wordEndIndex;
	private int wordLength;
	
	// 当前的下标
	private int index;
	
	// 每次获取的字符
	private char c;

	/**
	 * 从分页查询对象中解析出order by信息
	 * @param statement
	 * @return 是否解析到order by信息
	 */
	public boolean resolving(PageSqlStatement statement) {
		String sql = statement.getSql();
		index = sql.length()-1;
		
		
		while(index > -1) {
			c = sql.charAt(index);
			if(statement.isBlank(c)) {
				
			}else {
				
			}
			index--;
		}
		return false;
	}
}