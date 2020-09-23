package com.douglei.orm.configuration.impl.element.environment.mapping;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingEntity;
import com.douglei.orm.configuration.environment.mapping.MappingOP;

/**
 * 
 * @author DougLei
 */
public class DeleteMappingEntity extends MappingEntity{
	
	public DeleteMappingEntity(String code) {
		this(code, true);
	}
	public DeleteMappingEntity(String code, boolean opStruct) {
		super.code = code;
		super.opStruct = opStruct;
	}
	
	@Override
	public void setMapping(Mapping mapping) {
		if(mapping == null)
			throw new NullPointerException("不存在code为"+code+"的映射, 无法删除"); 
		super.mapping = mapping;
		super.type = mapping.getMappingType();
	}

	@Override
	public MappingOP getOp() {
		return MappingOP.DELETE;
	}
}
