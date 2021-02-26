package com.douglei.orm.mapping;

import java.io.Serializable;

import com.douglei.orm.mapping.metadata.AbstractMetadata;

/**
 * 
 * @author DougLei
 */
public abstract class Mapping implements Serializable{
	private String type;
	private AbstractMetadata metadata;
	
	protected Mapping(String type, AbstractMetadata metadata) {
		this.type = type;
		this.metadata = metadata;
	}

	/**
	 * 获取映射的类型
	 * @return
	 */
	public final String getType() {
		return type;
	}
	
	/**
	 * 获取映射唯一编码
	 * @return
	 */
	public final String getCode() {
		return metadata.getCode();
	}
	
	/**
	 * 获取元数据信息
	 * @return
	 */
	public final AbstractMetadata getMetadata() {
		return metadata;
	}
}
