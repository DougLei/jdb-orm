package com.douglei.orm.configuration.environment.mapping.store.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.store.MappingStore;
import com.douglei.orm.configuration.environment.mapping.store.NotExistsMappingException;
import com.douglei.orm.configuration.environment.mapping.store.RepeatedMappingException;
import com.douglei.tools.utils.CollectionUtil;

/**
 * 使用当前系统的内存空间存储映射信息
 * @author DougLei
 */
public class ApplicationMappingStore implements MappingStore {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationMappingStore.class);
	private Map<String, Mapping> mappings = new HashMap<String, Mapping>(32);
	
	@Override
	public void addMapping(Mapping mapping) throws RepeatedMappingException{
		String code = mapping.getCode();
		if(mappingExists(code)) {
			throw new RepeatedMappingException("已经存在相同code为["+code+"]的映射对象: " + mappings.get(code));
		}
		mappings.put(code, mapping);
	}
	
	@Override
	public void addMapping(Collection<Mapping> mappings) throws RepeatedMappingException {
		if(CollectionUtil.unEmpty(mappings)) {
			for (Mapping mapping : mappings) {
				addMapping(mapping);
			}
		}
	}

	@Override
	public void addOrCoverMapping(Mapping mapping) {
		String code = mapping.getCode();
		if(logger.isDebugEnabled() && mappingExists(code)) {
			logger.debug("覆盖已经存在code为[{}]的映射对象: {}", code, mappings.get(code));
		}
		mappings.put(code, mapping);
	}
	
	@Override
	public Mapping removeMapping(String code) {
		return mappings.remove(code);
	}
	
	@Override
	public void removeMapping(Collection<String> codes) {
		if(CollectionUtil.unEmpty(codes)) {
			for (String code : codes) {
				removeMapping(code);
			}
		}
	}
	
	@Override
	public Mapping getMapping(String code) throws NotExistsMappingException {
		Mapping mp = mappings.get(code);
		if(mp == null) {
			throw new NotExistsMappingException("不存在code为["+code+"]的映射对象");
		}
		return mp;
	}
	
	@Override
	public boolean mappingExists(String code) {
		return mappings.containsKey(code);
	}
	
	@Override
	public void destroy() throws DestroyException {
		if(logger.isDebugEnabled()) logger.debug("{} 开始 destroy", getClass().getName());
		if(CollectionUtil.unEmpty(mappings)) {
			mappings.clear();
			mappings = null;
		}
		if(logger.isDebugEnabled()) logger.debug("{} 结束 destroy", getClass().getName());
	}
}
