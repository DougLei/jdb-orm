package com.douglei.configuration.environment.mapping.cache.store.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.DestroyException;
import com.douglei.configuration.SelfCheckingException;
import com.douglei.configuration.environment.mapping.Mapping;
import com.douglei.configuration.environment.mapping.cache.store.MappingCacheStore;
import com.douglei.configuration.environment.mapping.cache.store.NotExistsMappingException;
import com.douglei.configuration.environment.mapping.cache.store.RepeatedMappingException;

/**
 * 使用当前系统的内存空间存储映射信息
 * @author DougLei
 */
public class ApplicationMappingCacheStore implements MappingCacheStore {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationMappingCacheStore.class);
	
	private Map<String, Mapping> mappings;
	
	@Override
	public void initialStoreSize(int size) {
		if(size < 1) {
			mappings = new HashMap<String, Mapping>(DEFAULT_STORE_SIZE);
		}else {
			mappings = new HashMap<String, Mapping>(size);
		}
	}
	
	@Override
	public void addMapping(Mapping mapping){
		String code = mapping.getCode();
		if(mappingExists(code)) {
			throw new RepeatedMappingException("已经存在code为["+code+"]的映射对象: " + mappings.get(code).getClass());
		}
		if(logger.isDebugEnabled()) {
			logger.debug("添加新的映射信息: {}", mapping.toString());
		}
		mappings.put(code, mapping);
	}
	
	@Override
	public void coverMapping(Mapping mapping) throws RepeatedMappingException {
		String code = mapping.getCode();
		if(logger.isDebugEnabled()) {
			if(mappingExists(code)) {
				logger.debug("覆盖映射信息时, 存在同code的旧信息: {}", mappings.get(code).toString());
			}
			logger.debug("进行覆盖的映射信息: {}", mapping.toString());
		}
		mappings.put(code, mapping);
	}
	
	@Override
	public void removeMapping(String mappingCode) {
		mappings.remove(mappingCode);
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
	public void doDestroy() throws DestroyException {
		if(mappings != null && mappings.size() > 0) {
			mappings.clear();
		}
	}

	@Override
	public void selfChecking() throws SelfCheckingException {
	}
}
