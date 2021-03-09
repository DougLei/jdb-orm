package com.douglei.orm.sql.statement;

/**
 * 
 * @author DougLei
 */
public class AutoIncrementID {
	private String sequence; // 序列名, 针对Oracle数据库

	/**
	 * 
	 * @param sequenceName 序列名, 针对Oracle数据库; 如果不是Oracle数据库, 则可传入null
	 */
	public AutoIncrementID(String sequence) {
		this.sequence = sequence;
	}
	
	/**
	 * 获取序列名, 针对Oracle数据库
	 * @return
	 */
	public String getSequence() {
		return sequence;
	}
}
