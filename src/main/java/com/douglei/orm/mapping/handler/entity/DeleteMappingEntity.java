package com.douglei.orm.mapping.handler.entity;

import com.douglei.orm.mapping.MappingProperty;
import com.douglei.orm.mapping.MappingTypeContainer;

/**
 * 
 * @author DougLei
 */
public class DeleteMappingEntity extends MappingEntity{
	private String code; // 映射code
	private int order; // 操作映射的顺序
	
	/**
	 * 
	 * @param code 映射的code
	 */
	public DeleteMappingEntity(String code) {
		this(code, true);
	}
	/**
	 * 
	 * @param code 映射的code
	 * @param opDatabaseObject 是否操作数据库对象
	 */
	public DeleteMappingEntity(String code, boolean opDatabaseObject) {
		this.code = code;
		super.opDatabaseObject = opDatabaseObject;
	}
	
	/**
	 * 设置MappingProperty值: type和order(非MappingProperty实例)
	 * @param mappingProperty
	 */
	public void setMappingPropertyValue(MappingProperty mappingProperty) {
		super.type = MappingTypeContainer.getMappingTypeByName(mappingProperty.getType());
		this.order = mappingProperty.getOrder();
	}

	@Override
	public Mode getMode() {
		return Mode.DELETE;
	}
	
	@Override
	public String getCode() {
		return code;
	}
	
	@Override
	public int getOrder() {
		return order;
	}
	
	@Override
	public String toString() {
		return "DeleteMappingEntity [code="+code+", opDatabaseObject="+opDatabaseObject+"]";
	}
}
