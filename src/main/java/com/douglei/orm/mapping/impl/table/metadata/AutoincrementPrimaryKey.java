package com.douglei.orm.mapping.impl.table.metadata;

import com.douglei.orm.mapping.metadata.Metadata;

/**
 * 
 * @author DougLei
 */
public class AutoincrementPrimaryKey implements Metadata{
	private String column; // 列名
	private String sequenceName; // 序列名
	
	public AutoincrementPrimaryKey(String column, String sequenceName) {
		this.column = column;
		this.sequenceName = sequenceName;
	}

	/**
	 * 获取自增主键关联的列名
	 * @return
	 */
	public String getColumn() {
		return column;
	}
	
	/**
	 *  获取自增主键的序列名(针对Oracle数据库)
	 * @return
	 */
	public String getSequenceName() {
		return sequenceName;
	}
}
