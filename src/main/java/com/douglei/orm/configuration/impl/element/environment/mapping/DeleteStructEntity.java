package com.douglei.orm.configuration.impl.element.environment.mapping;

import com.douglei.orm.configuration.environment.mapping.MappingEntity;
import com.douglei.orm.configuration.environment.mapping.MappingOP;
import com.douglei.orm.configuration.environment.mapping.MappingType;

/**
 * 
 * @author DougLei
 */
public class DeleteStructEntity extends MappingEntity {
	
	public DeleteStructEntity(String code, MappingType type) {
		if(type.opInMappingContainer())
			throw new IllegalArgumentException("要删除type="+type.getName()+"的映射, 请使用 [" + DeleteMappingEntity.class.getName() + "] 类");
		
		super.code = code;
		super.type = type;
		super.opStruct = true;
	}
	
	@Override
	public boolean mappingIsRequired() {
		return false;
	}

	@Override
	public MappingOP getOp() {
		return MappingOP.DELETE;
	}
}
