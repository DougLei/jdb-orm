package com.douglei.orm.mapping.handler.entity;

import com.douglei.orm.mapping.MappingType;

/**
 * 
 * @author DougLei
 */
public abstract class MappingEntity {
	protected MappingType type; // 映射的类型
	protected boolean opDatabaseObject; // 是否操作数据库对象
	protected boolean enableProperty; // 是否启用MappingProperty; 没有启用MappingProperty的Mapping, 是无法被动态操作的
	
	/**
	 * 获取操作映射的模式
	 * @return
	 */
	public abstract Mode getMode();
	
	/**
	 * 获取映射类型
	 * @return
	 */
	public final MappingType getType() {
		return type;
	}
	
	/**
	 * 是否操作数据库对象
	 * @return
	 */
	public final boolean opDatabaseObject() {
		return opDatabaseObject;
	}
	
	/**
	 * 启用MappingProperty
	 * @return
	 */
	public final MappingEntity enableProperty() {
		this.enableProperty = true;
		return this;
	}
	
	/**
	 * 是否启用MappingProperty
	 * @return
	 */
	public final boolean isEnableProperty() {
		return enableProperty;
	}

	/**
	 * 获取映射code
	 * @return
	 */
	public abstract String getCode();
	
	/**
	 * 获取操作映射的顺序
	 * @return
	 */
	public abstract int getOrder();
}
