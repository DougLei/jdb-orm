package com.douglei.orm.mapping.handler.entity;

import com.douglei.orm.mapping.type.MappingTypeContainer;
import com.douglei.orm.mapping.type.MappingTypeNameConstants;

/**
 * 
 * @author DougLei
 */
public class DeleteDatabaseStructEntity extends MappingEntity {
	
	/**
	 * 
	 * @param code
	 * @param typeName  通过 {@link MappingTypeNameConstants}, 传入框架支持的映射类型名 , 或传入自定义且完成注册({@link MappingTypeHandler.register(MappingType)})的映射类型名
	 */
	public DeleteDatabaseStructEntity(String code, String typeName) {
		super.type = MappingTypeContainer.getMappingTypeByName(typeName);
		if(type.opMappingContainer())
			throw new IllegalArgumentException("要删除类型为"+typeName+"的映射, 请使用 [" + DeleteMappingEntity.class.getName() + "] 类");
		
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
