package com.douglei.orm.sql;

/**
 * 当是insert语句时, 是否要返回自增的id值
 * @author DougLei
 */
public class ReturnID {
	private String oracleSequenceName; // 针对oracle数据库, 传入序列名

	public ReturnID() {}
	public ReturnID(String oracleSequenceName) {
		this.oracleSequenceName = oracleSequenceName;
	}
	
	public String getOracleSequenceName() {
		return oracleSequenceName;
	}
}
