package com.douglei.orm.configuration.impl.element.environment.mapping;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingEntity;
import com.douglei.orm.configuration.environment.mapping.MappingOP;
import com.douglei.orm.configuration.environment.mapping.ParseMappingException;

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
	public Mapping parseMapping() throws ParseMappingException { // 只能通过外部传入mapping, 所以不实现该方法
		return null;
	}
	
	@Override
	public void setMapping(Mapping mapping) {
		if(mapping == null)
			throw new NullPointerException("不存在code=["+code+"]的映射信息, 无法删除"); 
		super.mapping = mapping;
		super.type = mapping.getMappingType();
	}

	@Override
	public MappingOP getOp() {
		return MappingOP.DELETE;
	}
}
