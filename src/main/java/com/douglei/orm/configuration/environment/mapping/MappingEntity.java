package com.douglei.orm.configuration.environment.mapping;

/**
 * 
 * @author DougLei
 */
public abstract class MappingEntity {
	protected String code; // 映射的编码
	protected MappingType type; // 映射的类型
	protected Mapping mapping; // mapping实例
	protected boolean opStruct; // 是否操作结构
	
	/**
	 * 获取对映射进行的操作
	 * @return
	 */
	public abstract MappingOP getOp();
	
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
	 * 是否要操作结构
	 * @return
	 */
	public boolean opStruct() {
		return opStruct;
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
		return getClass().getName() + " [code=" + code + ", type=" + type + ", op=" + getOp() + "]";
	}
}
