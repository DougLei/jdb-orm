package com.douglei.orm.mapping;

import java.io.Serializable;

import com.douglei.orm.mapping.metadata.AbstractMetadata;
import com.douglei.orm.mapping.type.MappingTypeConstants;

/**
 * 
 * @author DougLei
 */
public abstract class Mapping implements Serializable{
	private static final long serialVersionUID = 7284961759669114076L;
	
	private String type;
	private AbstractMetadata metadata;
	private transient MappingProperty property;
	
	public Mapping(String type, AbstractMetadata metadata) {
		this(type, metadata, null);
	}
	public Mapping(String type, AbstractMetadata metadata, MappingProperty property) {
		this.type = type;
		this.metadata = metadata;
		this.property = property==null?new MappingProperty(metadata.getCode(), type):property;
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
	 * 获取属性
	 * @return
	 */
	public final MappingProperty getProperty() {
		return property;
	}
	
	/**
	 * 获取元数据信息
	 * @return
	 */
	public final AbstractMetadata getMetadata() {
		return metadata;
	}
}
