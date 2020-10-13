package com.douglei.orm.mapping.container.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.MappingFeature;
import com.douglei.orm.mapping.container.MappingContainer;

/**
 * 使用当前系统的内存空间作为映射容器
 * @author DougLei
 */
public class ApplicationMappingContainer implements MappingContainer {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationMappingContainer.class);
	private Map<String, Mapping> mappings = new HashMap<String, Mapping>(64);
	private Map<String, MappingFeature> mappingFeatures = new HashMap<String, MappingFeature>(64);
	
	@Override
	public void clear() {
		if(!mappings.isEmpty())
			mappings.clear();
		if(!mappingFeatures.isEmpty())
			mappingFeatures.clear();
	}
	
	@Override
	public MappingFeature addMappingFeature(MappingFeature mappingFeature) {
		String code = mappingFeature.getCode();
		MappingFeature exMappingFeature = getMappingFeature(code);
		if(logger.isDebugEnabled() && exMappingFeature != null) 
			logger.debug("覆盖code为[{}]的映射特性: {}", code, exMappingFeature);

		mappingFeatures.put(code, mappingFeature);
		return exMappingFeature;
	}

	@Override
	public MappingFeature deleteMappingFeature(String code) {
		return mappingFeatures.remove(code);
	}

	@Override
	public MappingFeature getMappingFeature(String code) {
		return mappingFeatures.get(code);
	}
	
	@Override
	public Mapping addMapping(Mapping mapping) {
		String code = mapping.getCode();
		Mapping exMapping = getMapping(code);
		if(logger.isDebugEnabled() && exMapping != null) 
			logger.debug("覆盖code为[{}]的映射: {}", code, exMapping);
		
		mappings.put(code, mapping);
		return exMapping;
	}
	
	@Override
	public Mapping deleteMapping(String code) {
		return mappings.remove(code);
	}
	
	@Override
	public Mapping getMapping(String code) {
		return mappings.get(code);
	}
	
	@Override
	public boolean exists(String code) {
		return mappings.containsKey(code);
	}
}
