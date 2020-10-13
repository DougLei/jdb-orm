package com.douglei.orm.mapping.handler.entity.impl;

import com.douglei.orm.mapping.handler.entity.MappingEntity;
import com.douglei.orm.mapping.handler.entity.MappingOP;
import com.douglei.orm.mapping.type.MappingTypeConstants;
import com.douglei.orm.mapping.type.MappingTypeContainer;

/**
 * 
 * @author DougLei
 */
public class DeleteDatabaseObjectOnlyEntity extends MappingEntity {
	
	/**
	 * 
	 * @param code
	 * @param type  通过 {@link MappingTypeConstants}, 传入框架支持的映射类型名 , 或传入自定义且完成注册({@link MappingTypeHandler.register(MappingType)})的映射类型名
	 */
	public DeleteDatabaseObjectOnlyEntity(String code, String type) {
		super.type = MappingTypeContainer.getMappingTypeByName(type);
		if(super.type.supportOpMappingContainer())
			throw new IllegalArgumentException("要删除类型为["+type+"]的映射, 请使用 [" + DeleteMappingEntity.class.getName() + "] 类");
		
		super.code = code.toUpperCase();
		super.opDatabaseObject = true;
	}
	
	@Override
	public MappingOP getOp() {
		return MappingOP.DELETE_DATABASE_OBJECT_ONLY;
	}
}
