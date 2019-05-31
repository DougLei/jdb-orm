package com.douglei.configuration.environment.mapping.cache.store.impl;

import java.util.HashMap;
import java.util.Map;

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
		mappings.put(code, mapping);
	}
	
	@Override
	public void coverMapping(Mapping mapping) throws RepeatedMappingException {
		mappings.put(mapping.getCode(), mapping);
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
