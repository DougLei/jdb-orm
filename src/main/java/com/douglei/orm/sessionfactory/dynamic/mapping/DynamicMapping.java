package com.douglei.orm.sessionfactory.dynamic.mapping;

import com.douglei.orm.configuration.environment.mapping.MappingType;

/**
 * 
 * @author DougLei
 */
public class DynamicMapping {
	
	private DynamicMappingType type;
	private String code;
	private String mappingConfigurationFilePath;
	private MappingType mappingType;
	private String mappingConfigurationContent;
	
	public DynamicMapping(String mappingConfigurationFilePath) {
		this.type = DynamicMappingType.BY_PATH;
		this.mappingConfigurationFilePath = mappingConfigurationFilePath;
	}
	public DynamicMapping(MappingType mappingType, String mappingConfigurationContent) {
		this.type = DynamicMappingType.BY_CONTENT;
		this.mappingType = mappingType;
		this.mappingConfigurationContent = mappingConfigurationContent;
	}

	public DynamicMappingType getType() {
		return type;
	}
	public String getMappingConfigurationFilePath() {
		return mappingConfigurationFilePath;
	}
	public MappingType getMappingType() {
		return mappingType;
	}
	public String getMappingConfigurationContent() {
		return mappingConfigurationContent;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		if(this.code == null) {
			this.code = code;
		}
	}
}
