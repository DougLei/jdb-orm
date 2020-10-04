package com.douglei.orm.mapping.container.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.container.MappingContainer;

/**
 * 使用当前系统的内存空间存储映射信息
 * @author DougLei
 */
public class ApplicationMappingContainer implements MappingContainer {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationMappingContainer.class);
	private Map<String, Mapping> mappings = new HashMap<String, Mapping>(64);
	
	@Override
	public void clear() {
		mappings.clear();
	}
	
	@Override
	public Mapping addMapping(Mapping mapping) {
		String code = mapping.getCode();
		Mapping exMapping = getMapping(code);
		if(logger.isDebugEnabled() && exMapping != null) {
			logger.debug("覆盖已经存在id为[{}]的映射对象: {}", code, mappings.get(code));
		}
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
