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
	public DeleteMappingEntity(String code, boolean opDatabaseStruct) {
		super.code = code;
		super.opDatabaseStruct = opDatabaseStruct;
	}
	
	public void setMapping(Mapping mapping) {
		super.mapping = mapping;
		super.type = MappingTypeContainer.getMappingTypeByName(mapping.getType());
	}

	@Override
	public MappingOP getOp() {
		return MappingOP.DELETE;
	}
}
