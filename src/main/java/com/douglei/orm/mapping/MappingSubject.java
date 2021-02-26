package com.douglei.orm.mapping;

/**
 * 映射主体
 * @author DougLei
 */
public class MappingSubject {
	private MappingProperty mappingProperty;
	private Mapping mapping;
	
	public MappingSubject(MappingProperty mappingProperty, Mapping mapping) {
		this.mappingProperty = mappingProperty;
		this.mapping = mapping;
	}
	
	/**
	 * 获取映射属性实例
	 * @return
	 */
	public MappingProperty getMappingProperty() {
		return mappingProperty;
	}
	/**
	 * 获取映射实例
	 * @return
	 */
	public Mapping getMapping() {
		return mapping;
	}
}
