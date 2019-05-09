package com.douglei.configuration.environment.mapping;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.SelfProcessing;

/**
 * 
 * @author DougLei
 */
public abstract class MappingWrapper implements SelfProcessing{
	private static final Logger logger = LoggerFactory.getLogger(MappingWrapper.class);
	
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
	protected void addMapping(Mapping mapping){
		if(mapping == null) {
			throw new NullPointerException("要添加的"+Mapping.class+"实例不能为空");
		}
		String code = mapping.getCode();
		if(mappings.containsKey(code)) {
			throw new RepeatMappingCodeException("已经存在code为["+code+"]的映射对象: " + mappings.get(code).getClass());
		}
		if(logger.isDebugEnabled()) {
			logger.debug("添加新的映射信息: {}", mapping.toString());
		}
		mappings.put(code, mapping);
	}
	
	/**
	 * 动态添加映射, 如果存在, 则覆盖
	 * @param mappingConfigurationContent
	 */
	public abstract void dynamicAddMapping(String mappingConfigurationContent);
	
	/**
	 * 覆盖映射
	 * @param mapping
	 */
	protected void coverMapping(Mapping mapping) {
		if(mapping == null) {
			throw new NullPointerException("要添加的"+Mapping.class+"实例不能为空");
		}
		String code = mapping.getCode();
		if(logger.isDebugEnabled()) {
			if(mappings.containsKey(code)) {
				logger.debug("覆盖映射信息时, 存在同code的旧信息: {}", mappings.get(code).toString());
			}
			logger.debug("进行覆盖的映射信息: {}", mapping.toString());
		}
		mappings.put(code, mapping);
	}
	
	/**
	 * 动态移除映射
	 * @param mappingCode
	 */
	public void dynamicRemoveMapping(String mappingCode) {
		mappings.remove(mappingCode);
	}
	
	/**
	 * 获取映射
	 * @param mappingCode
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
