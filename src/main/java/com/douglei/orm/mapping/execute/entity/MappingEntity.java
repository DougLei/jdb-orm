package com.douglei.orm.mapping.execute.entity;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.execute.ParseMappingException;
import com.douglei.orm.mapping.type.MappingType;

/**
 * 
 * @author DougLei
 */
public abstract class MappingEntity {
	protected String code; // 映射的编码
	protected MappingType type; // 映射的类型
	protected Mapping mapping; // mapping实例
	protected boolean opDatabaseStruct; // 是否操作数据库结构
	
	/**
	 * 获取对映射进行的操作
	 * @return
	 */
	public abstract MappingOP getOp();
	
	/**
	 * 映射实例是否是必须的, 默认为true; 如果是必须的, 则必须进行parseMapping或setMapping
	 * @return
	 */
	public boolean mappingIsRequired() {
		return true;
	}
	
	/**
	 * 解析出mapping实例
	 * @return 解析是否成功
	 * @throws ParseMappingException
	 */
	public boolean parseMapping() throws ParseMappingException {
		return false;
	}
	
	/**
	 * 将外部的mapping实例set进来
	 * @param mapping
	 */
	public void setMapping(Mapping mapping) {
	}
	
	/**
	 * 是否要操作数据库结构
	 * @return
	 */
	public boolean opDatabaseStruct() {
		return opDatabaseStruct;
	}
	
	public String getCode() {
		return code;
	}
	public MappingType getType() {
		return type;
	}
	public Mapping getMapping() {
		return mapping;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [mappingIsRequired="+mappingIsRequired()+", code=" + code + ", type=" + type + ", op=" + getOp() + "]";
	}
}
