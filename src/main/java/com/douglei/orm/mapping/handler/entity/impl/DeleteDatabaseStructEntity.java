package com.douglei.orm.mapping.handler.entity.impl;

import com.douglei.orm.mapping.handler.entity.MappingEntity;
import com.douglei.orm.mapping.handler.entity.MappingOP;
import com.douglei.orm.mapping.type.MappingTypeContainer;
import com.douglei.orm.mapping.type.MappingTypeConstants;

/**
 * 
 * @author DougLei
 */
public class DeleteDatabaseStructEntity extends MappingEntity {
	
	/**
	 * 
	 * @param code
	 * @param type  通过 {@link MappingTypeConstants}, 传入框架支持的映射类型名 , 或传入自定义且完成注册({@link MappingTypeHandler.register(MappingType)})的映射类型名
	 */
	public DeleteDatabaseStructEntity(String code, String type) {
		super.type = MappingTypeContainer.getMappingTypeByName(type);
		if(super.type.opMappingContainer())
			throw new IllegalArgumentException("要删除类型为"+type+"的映射, 请使用 [" + DeleteMappingEntity.class.getName() + "] 类");
		
		super.code = code;
		super.opDatabaseStruct = true;
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
