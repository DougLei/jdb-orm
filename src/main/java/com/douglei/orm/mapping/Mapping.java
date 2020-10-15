package com.douglei.orm.mapping;

import java.io.Serializable;

import com.douglei.orm.mapping.metadata.AbstractMetadata;
import com.douglei.orm.mapping.type.MappingTypeConstants;

/**
 * 
 * @author DougLei
 */
public abstract class Mapping implements Serializable{
	
	private String type;
	private transient MappingFeature feature;
	private AbstractMetadata metadata;
	
	public Mapping(String type, AbstractMetadata metadata) {
		this(type, metadata, null);
	}
	public Mapping(String type, AbstractMetadata metadata, MappingFeature feature) {
		this.type = type;
		this.metadata = metadata;
		this.feature = feature;
	}

	/**
	 * 获取映射唯一标识
	 * @return
	 */
	public final String getCode() {
		return metadata.getCode();
	}
	
	/**
	 * 获取映射的类型
	 * @return 该方法会返回的值有:  {@link MappingTypeConstants}中的映射类型名, 或自定义且完成注册的映射类型名
	 */
	public final String getType() {
		return type;
	}
	
	/**
	 * 获取特性
	 * @return
	 */
	public final MappingFeature getFeature() {
		return feature;
	}
	
	/**
	 * 获取元数据信息
	 * @return
	 */
	public final AbstractMetadata getMetadata() {
		return metadata;
	}
}
