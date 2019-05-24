package com.douglei.configuration.environment.property.mapping.store.target;

import java.util.HashMap;
import java.util.Map;

import com.douglei.configuration.environment.property.mapping.store.target.impl.ApplicationMappingStore;
import com.douglei.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class MappingStoreContext {
	private static final int count = MappingStoreType.values().length;
	
	private static final Map<String, Class<? extends MappingStore>> CLASS_MAP = new HashMap<String, Class<? extends MappingStore>>(count);
	static {
		CLASS_MAP.put(ApplicationMappingStore.TYPE.getCode(), ApplicationMappingStore.class);
		// TODO 后续可以集成其他缓存, 存储映射信息, 如果这里新加了其他缓存实现, 请修改cache map集合的初始化size值
	}
	
	public static MappingStore getMappingStore(String type) {
		type = type.toUpperCase();
		Class<? extends MappingStore> mappingStoreClass = CLASS_MAP.get(type);
		if(mappingStoreClass == null) {
			throw new NullPointerException("系统目前不支持["+type+"], 目前支持的mappingStore值包括:"+CLASS_MAP.keySet());
		}
		return (MappingStore) ConstructorUtil.newInstance(mappingStoreClass);
	}
}
