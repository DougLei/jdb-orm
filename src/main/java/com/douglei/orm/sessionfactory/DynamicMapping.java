package com.douglei.orm.sessionfactory;

import com.douglei.orm.configuration.environment.mapping.MappingType;

/**
 * 动态mapping
 * @author DougLei
 */
public class DynamicMapping {
	private String mappingCode;
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
	public String getMappingCode() {
		return mappingCode;
	}
	public void setMappingCode(String mappingCode) {
		if(this.mappingCode == null) {
			this.mappingCode = mappingCode;
		}
	}
}
