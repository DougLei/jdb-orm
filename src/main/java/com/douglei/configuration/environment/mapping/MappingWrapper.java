package com.douglei.configuration.environment.mapping;

import java.util.HashMap;
import java.util.Map;

import com.douglei.configuration.SelfProcessing;

/**
 * 
 * @author DougLei
 */
public abstract class MappingWrapper implements SelfProcessing{
	protected static final int DEFAULT_MAPPINGS_SIZE = 32;
	
	protected Map<String, Mapping> mappings;
	
	/**
	 * 初始化默认的mapping map集合
	 */
	protected void initialDefaultMappingsMap() {
		mappings = new HashMap<String, Mapping>(DEFAULT_MAPPINGS_SIZE);
	}
	
	/**
	 * 添加映射
	 * <pre>
	 * 	如果已经存在相同code的mapping，则抛出异常
	 * </pre>
	 * @param mapping
	 */
	public void addMapping(Mapping mapping){
		if(mapping == null) {
			throw new NullPointerException("要添加的"+Mapping.class+"实例不能为空");
		}
		String code = mapping.getCode();
		if(mappings.containsKey(code)) {
			throw new RepeatMappingCodeException("已经存在code为["+code+"]的映射对象: " + mappings.get(code).getClass());
		}
		mappings.put(code, mapping);
	}
	
	/**
	 * 覆盖映射
	 * <pre>
	 * 	如果已经存在相同code的mapping，则将之前的remove，再添加新的
	 * </pre>
	 * @param mapping
	 */
	public void coverMapping(Mapping mapping) {
		if(mapping == null) {
			throw new NullPointerException("要添加的"+Mapping.class+"实例不能为空");
		}
		String code = mapping.getCode();
		if(mappings.containsKey(code)) {
			removeMapping(code);
		}
		mappings.put(code, mapping);
	}
	
	/**
	 * 移除映射
	 * @param mappingName
	 */
	public void removeMapping(String mappingCode) {
		Object mp = mappings.remove(mappingCode);
		if(mp == null) {
			throw new NullPointerException("不存在code为["+mappingCode+"]的映射对象");
		}
	}
	
	/**
	 * 获取映射
	 * @param mappingName
	 * @return
	 */
	public Mapping getMapping(String mappingCode) {
		Mapping mp = mappings.get(mappingCode);
		if(mp == null) {
			throw new NullPointerException("不存在code为["+mappingCode+"]的映射对象");
		}
		return mp;
	}
}
