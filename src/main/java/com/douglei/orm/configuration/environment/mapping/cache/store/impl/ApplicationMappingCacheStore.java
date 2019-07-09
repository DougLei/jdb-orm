package com.douglei.orm.configuration.environment.mapping.cache.store.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.SelfCheckingException;
import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.cache.store.MappingCacheStore;
import com.douglei.orm.configuration.environment.mapping.cache.store.NotExistsMappingException;
import com.douglei.orm.configuration.environment.mapping.cache.store.RepeatedMappingException;

/**
 * 使用当前系统的内存空间存储映射信息
 * @author DougLei
 */
public class ApplicationMappingCacheStore implements MappingCacheStore {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationMappingCacheStore.class);
	
	private Map<String, Mapping> mappings;
	
	@Override
	public void initializeStore(int size) {
		if(mappings != null) {
			mappings.clear();
			mappings = null;
		}
		if(size < 1) {
			mappings = new HashMap<String, Mapping>(DEFAULT_STORE_SIZE);
		}else {
			mappings = new HashMap<String, Mapping>(size);
		}
	}
	
	@Override
	public void addMapping(Mapping mapping) throws RepeatedMappingException{
		String code = mapping.getCode();
		if(mappingExists(code)) {
			throw new RepeatedMappingException("已经存在code为["+code+"]的映射对象: " + mappings.get(code));
		}
		mappings.put(code, mapping);
	}
	
	@Override
	public void addMapping(Collection<Mapping> mappings) throws RepeatedMappingException {
		if(mappings != null) {
			for (Mapping mapping : mappings) {
				addMapping(mapping);
			}
		}
	}

	@Override
	public void addOrCoverMapping(Mapping mapping) {
		String code = mapping.getCode();
		if(logger.isDebugEnabled() && mappingExists(code)) {
			logger.debug("覆盖已经存在code为[]的映射对象: {}", code, mappings.get(code));
		}
		mappings.put(code, mapping);
	}
	
	@Override
	public void addOrCoverMapping(Collection<Mapping> mappings) {
		if(mappings != null) {
			for (Mapping mapping : mappings) {
				addOrCoverMapping(mapping);
			}
		}
	}

	@Override
	public Mapping removeMapping(String mappingCode) throws NotExistsMappingException {
		Mapping mp = mappings.remove(mappingCode);
		if(mp == null) {
			throw new NotExistsMappingException("不存在code为["+mappingCode+"]的映射对象, 无法删除");
		}
		return mp;
	}
	
	@Override
	public void removeMapping(Collection<String> mappingCodes) throws NotExistsMappingException {
		if(mappings != null) {
			for (String mappingCode : mappingCodes) {
				removeMapping(mappingCode);
			}
		}
	}
	
	@Override
	public Mapping getMapping(String mappingCode) throws NotExistsMappingException {
		Mapping mp = mappings.get(mappingCode);
		if(mp == null) {
			throw new NotExistsMappingException("不存在code为["+mappingCode+"]的映射对象");
		}
		return mp;
	}
	
	@Override
	public boolean mappingExists(String mappingCode) {
		return mappings.containsKey(mappingCode);
	}
	
	@Override
	public void destroy() throws DestroyException {
		if(mappings != null && mappings.size() > 0) {
			mappings.clear();
		}
	}

	@Override
	public void selfChecking() throws SelfCheckingException {
	}
}
