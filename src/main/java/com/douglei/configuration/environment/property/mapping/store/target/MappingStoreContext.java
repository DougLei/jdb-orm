package com.douglei.configuration.environment.property.mapping.store.target;

import java.util.HashMap;
import java.util.Map;

import com.douglei.configuration.environment.property.mapping.store.target.impl.ApplicationMappingStore;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class MappingStoreContext {
	private static final Map<String, MappingStore> cache = new HashMap<String, MappingStore>(1);
	static {
		register(new ApplicationMappingStore());
		
		// 后续可以集成其他缓存, 存储映射信息, 如果这里新加了其他缓存实现, 请修改cache map集合的初始化size值
		
	}
	private static void register(MappingStore mappingStore) {
		cache.put(mappingStore.getType(), mappingStore);
	}
	
	
	public static boolean containsType(String type) {
		if(StringUtil.isEmpty(type)) {
			return false;
		}
		return cache.containsKey(type);
	}
	
	public static MappingStore getMappingStore(String type) {
		return cache.get(type);
	}
}
