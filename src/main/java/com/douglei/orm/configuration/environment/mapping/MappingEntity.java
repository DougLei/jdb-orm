package com.douglei.orm.configuration.environment.mapping;

/**
 * 
 * @author DougLei
 */
public abstract class MappingEntity {
	protected String code; // 映射的编码
	protected MappingType type; // 映射的类型
	protected Mapping mapping; // mapping实例
	
	/**
	 * 获取对映射进行的操作
	 * @return
	 */
	public abstract MappingOP getOp();
	
	/**
	 * 解析出mapping实例
	 * @return 返回解析出的mapping实例
	 * @throws ParseMappingException
	 */
	public abstract Mapping parseMapping() throws ParseMappingException;
	
	/**
	 * 将外部的mapping实例set进来
	 * @param mapping
	 */
	public abstract void setMapping(Mapping mapping);
	
	
	public String getCode() {
		return code;
	}
	public MappingType getType() {
		return type;
	}
	public Mapping getMapping() {
		return mapping;
	}
}
