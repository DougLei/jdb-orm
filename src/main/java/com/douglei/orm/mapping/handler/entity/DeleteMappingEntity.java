package com.douglei.orm.mapping.handler.entity;

import com.douglei.orm.mapping.Mapping;
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
	
	@Override
	public void setMapping(Mapping mapping) {
		if(mapping == null)
			throw new NullPointerException("不存在code为"+code+"的映射, 无法删除"); 
		super.mapping = mapping;
		super.type = MappingTypeContainer.getMappingTypeByName(mapping.getType());
	}

	@Override
	public MappingOP getOp() {
		return MappingOP.DELETE;
	}
}
