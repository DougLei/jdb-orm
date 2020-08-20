package com.douglei.orm.core.sql;

/**
 * 当是insert语句时, 是否要返回自增的id值
 * @author DougLei
 */
public class ReturnID {
	/**
	 * 可为空, 针对oracle数据库, 传入 (序列名.currval) 的sql语句
	 * 例如: sys_user_seq.currval, 底层会拼接成 select sys_user_seq.currval from dual来获取当前的序列值
	 */
	private String oracleSeqCurrvalSQL;

	public ReturnID(String oracleSeqCurrvalSQL) {
		this.oracleSeqCurrvalSQL = oracleSeqCurrvalSQL;
	}
	public String getOracleSeqCurrvalSQL() {
		return oracleSeqCurrvalSQL;
	}
}
