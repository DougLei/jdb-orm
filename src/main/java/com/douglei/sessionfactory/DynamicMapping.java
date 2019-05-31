package com.douglei.sessionfactory;

import com.douglei.configuration.environment.mapping.MappingType;

/**
 * 动态mapping
 * @author DougLei
 */
public class DynamicMapping {
	private MappingType mappingType;
	private String mappingConfigurationContent;

	public DynamicMapping(MappingType mappingType, String mappingConfigurationContent) {
		this.mappingType = mappingType;
		this.mappingConfigurationContent = mappingConfigurationContent;
	}

	public MappingType getMappingType() {
		return mappingType;
	}
	public String getMappingConfigurationContent() {
		return mappingConfigurationContent;
	}
}
