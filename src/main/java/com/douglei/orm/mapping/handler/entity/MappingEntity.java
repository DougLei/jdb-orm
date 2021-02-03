package com.douglei.orm.mapping.handler.entity;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.metadata.type.MetadataType;

/**
 * 
 * @author DougLei
 */
public abstract class MappingEntity {
	protected String code; // 映射的编码
	protected MetadataType type; // 映射的类型
	protected int order; // 映射的顺序
	protected Mapping mapping; // mapping实例
	protected boolean opDatabaseObject; // 是否操作数据库对象
	
	/**
	 * 获取对映射进行的操作
	 * @return
	 */
	public abstract MappingOP getOp();
	
	public final String getCode() {
		return code;
	}
	public final MetadataType getType() {
		return type;
	}
	public final int getOrder() {
		return order;
	}
	public final Mapping getMapping() {
		return mapping;
	}
	public final boolean opDatabaseObject() {
		return opDatabaseObject;
	}
	
	@Override
	public final String toString() {
		return getClass().getSimpleName() + " [code=" + code + "]";
	}
}
