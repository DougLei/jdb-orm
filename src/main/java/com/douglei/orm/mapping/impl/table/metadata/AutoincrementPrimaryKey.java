package com.douglei.orm.mapping.impl.table.metadata;

import com.douglei.orm.mapping.metadata.Metadata;

/**
 * 
 * @author DougLei
 */
public class AutoincrementPrimaryKey implements Metadata{
	private String column; // 列名
	private String code; // 列code
	private String sequence; // 序列名
	
	public AutoincrementPrimaryKey(String column, String code, String sequence) {
		this.column = column;
		this.code = code;
		this.sequence = sequence;
	}

	/**
	 * 获取自增主键关联的列名
	 * @return
	 */
	public String getColumn() {
		return column;
	}
	
	/**
	 * 获取自增主键关联的列code
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 *  获取自增主键的序列名(针对Oracle数据库)
	 * @return
	 */
	public String getSequence() {
		return sequence;
	}
}
