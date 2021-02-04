package com.douglei.orm.mapping.type;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author DougLei
 */
public class MappingTypeContainer {
	private static final Logger logger = LoggerFactory.getLogger(MappingTypeContainer.class);
	private final Map<String, MappingType> container = new HashMap<String, MappingType>();
	
	/**
	 * 注册MappingType
	 * @param type
	 */
	public void register(MappingType type) {
		if(container.containsKey(type.getName()))
			throw new MappingTypeException("已存在名为["+type.getName()+"]的映射类型");
		
		for(MappingType mt : container.values()) {
			if(mt.getFileSuffix().equals(type.getFileSuffix()))
				throw new MappingTypeException("已存在文件后缀为["+type.getFileSuffix()+"]的映射类型");
		}
		
		logger.debug("注册name为["+type.getName()+"]的映射类型");
		container.put(type.getName(), type);
	}
	
	/**
	 * 
	 * @param name 映射类型名称, 可参考{@link MappingTypeNameConstants}
	 * @return
	 */
	public MappingType getMappingTypeByName(String name) {
		MappingType type = container.get(name);
		if(type == null)
			throw new MappingTypeException("不存在名为["+name+"]的映射类型");
		return type;
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public MappingType getMappingTypeByFile(String file) {
		for(MappingType mappingType : container.values()) {
			if(file.endsWith(mappingType.getFileSuffix()))
				return mappingType;
		}
		throw new MappingTypeException("不存在文件后缀为["+file+"]的映射类型");
	}
	
	/**
	 * 获取目前容器中所有映射类型支持的文件后缀数组
	 * @return
	 */
	public String[] getFileSuffixes() {
		String[] fileSuffixes = new String[container.size()];
		
		int index = 0;
		for(MappingType mt : container.values()) 
			fileSuffixes[index++] = mt.getFileSuffix();
			
		return fileSuffixes;
	}
}
