package com.douglei.orm.mapping.impl.table.metadata;

import com.douglei.orm.mapping.metadata.Metadata;

/**
 * 
 * @author DougLei
 */
public class PrimaryKeyHandlerMetadata implements Metadata{
	private String type;
	private String value;
	
	public PrimaryKeyHandlerMetadata(String type, String value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * 获取主键处理器类型
	 * @return
	 */
	public String getType() {
		return type;
	}
	/**
	 * 获取主键处理器需要的值
	 * @return
	 */
	public String getValue() {
		return value;
	}
}
