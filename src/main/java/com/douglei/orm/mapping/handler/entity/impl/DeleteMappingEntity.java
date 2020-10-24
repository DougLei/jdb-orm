package com.douglei.orm.mapping.handler.entity.impl;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.handler.entity.MappingEntity;
import com.douglei.orm.mapping.handler.entity.MappingOP;
import com.douglei.orm.mapping.type.MappingTypeContainer;

/**
 * 
 * @author DougLei
 */
public class DeleteMappingEntity extends MappingEntity{
	
	public DeleteMappingEntity(String code) {
		this(code, true);
	}
	public DeleteMappingEntity(String code, boolean opDatabaseObject) {
		super.code = code;
		super.opDatabaseObject = opDatabaseObject;
	}
	
	public void setType(String type) {
		super.type = MappingTypeContainer.getMappingTypeByName(type);
	}
	public void setOrder(int order) {
		super.order = order;
	}
	public void setMapping(Mapping mapping) {
		super.mapping = mapping;
	}

	@Override
	public MappingOP getOp() {
		return MappingOP.DELETE;
	}
}
