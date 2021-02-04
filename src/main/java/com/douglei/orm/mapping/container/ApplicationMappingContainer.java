package com.douglei.orm.mapping.container;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.MappingProperty;

/**
 * 
 * @author DougLei
 */
public class ApplicationMappingContainer implements MappingContainer {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationMappingContainer.class);
	private Map<String, Mapping> mappings = new HashMap<String, Mapping>(64);
	private Map<String, MappingProperty> mappingProperties = new HashMap<String, MappingProperty>(64);
	
	@Override
	public MappingProperty addMappingProperty(MappingProperty mappingProperty) {
		String code = mappingProperty.getCode();
		MappingProperty exMappingProperty = mappingProperties.get(code);
		if(logger.isDebugEnabled() && exMappingProperty != null) 
			logger.debug("覆盖code为[{}]的映射属性: {}", code, exMappingProperty);

		mappingProperties.put(code, mappingProperty);
		return exMappingProperty;
	}

	@Override
	public MappingProperty deleteMappingProperty(String code) {
		return mappingProperties.remove(code);
	}

	@Override
	public MappingProperty getMappingProperty(String code) {
		return mappingProperties.get(code);
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
	
	@Override
	public void clear() {
		if(!mappings.isEmpty())
			mappings.clear();
		if(!mappingProperties.isEmpty())
			mappingProperties.clear();
	}
}
