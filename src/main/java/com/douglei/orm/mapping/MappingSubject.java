package com.douglei.orm.mapping;

/**
 * 映射主体
 * @author DougLei
 */
public class MappingSubject {
	private MappingProperty property;
	private Mapping mapping;
	
	MappingSubject(MappingProperty property, Mapping mapping) {
		this.property = property;
		this.mapping = mapping;
	}
	
	/**
	 * 获取映射属性实例
	 * @return
	 */
	public MappingProperty getMappingProperty() {
		return property;
	}
	/**
	 * 获取映射实例
	 * @return
	 */
	public Mapping getMapping() {
		return mapping;
	}
}
