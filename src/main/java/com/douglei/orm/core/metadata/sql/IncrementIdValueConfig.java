package com.douglei.orm.core.metadata.sql;

/**
 * 自增主键值的配置
 * @author DougLei
 */
public class IncrementIdValueConfig {
	private String key;
	private String oracleSequenceName;
	
	public IncrementIdValueConfig(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	public String getOracleSequenceName() {
		return oracleSequenceName;
	}
	public void setOracleSequenceName(String oracleSequenceName) {
		this.oracleSequenceName = oracleSequenceName;
	}
}
