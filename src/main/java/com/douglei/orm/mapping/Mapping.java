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
	private AbstractMetadata metadata;
	private transient MappingProperty property;
	
	/**
	 * 
	 * @param type
	 * @param metadata
	 * @param property 映射属性, 当映射的类型支持操作映射容器(即supportOpMappingContainer=true)时, 必须传入实例, 否则可以传入null;  <b>本构造器中没有做任何容错处理, 需要使用者按照要求传入参数</b>
	 */
	public Mapping(String type, AbstractMetadata metadata, MappingProperty property) {
		this.type = type;
		this.metadata = metadata;
		this.property = property;
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
