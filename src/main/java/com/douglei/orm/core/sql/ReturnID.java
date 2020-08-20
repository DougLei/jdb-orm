package com.douglei.orm.core.sql;

/**
 * 当是insert语句时, 是否要返回自增的id值
 * @author DougLei
 */
public class ReturnID {
	private String oracleSequenceName;

	/**
	 * 
	 * @param oracleSequenceName 可为空, 针对oracle数据库, 传入序列名
	 */
	public ReturnID(String oracleSequenceName) {
		this.oracleSequenceName = oracleSequenceName;
	}
	public String getOracleSequenceName() {
		return oracleSequenceName;
	}
}
