package com.douglei.configuration.environment.property.mapping.store.target;

import java.util.HashMap;
import java.util.Map;

import com.douglei.configuration.environment.property.mapping.store.target.impl.ApplicationMappingCacheStore;
import com.douglei.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class MappingCacheStoreMap {
	private static final Map<String, Class<? extends MappingCacheStore>> MAPPING_CACHE_STORE_CLASS_MAP = new HashMap<String, Class<? extends MappingCacheStore>>(4);
	static {
		MAPPING_CACHE_STORE_CLASS_MAP.put("application", ApplicationMappingCacheStore.class);
		// TODO 后续可以集成其他缓存, 存储映射信息
	}
	
	public static MappingCacheStore getMappingCacheStore(String type) {
		type = type.toLowerCase();
		Class<? extends MappingCacheStore> mappingCacheStoreClass = MAPPING_CACHE_STORE_CLASS_MAP.get(type);
		if(mappingCacheStoreClass == null) {
			Object obj = null;
			try {
				obj = ConstructorUtil.newInstance(type);
			} catch (Exception e) {
				throw new UnsupportMappingCacheStoreException("系统目前不支持["+type+"], 目前支持的mappingCacheStore值包括:"+MAPPING_CACHE_STORE_CLASS_MAP.keySet());
			}
			if(!(obj instanceof MappingCacheStore)) {
				throw new UnsupportMappingCacheStoreException("["+type+"]的类必须继承["+MappingCacheStore.class.getName()+"]");
			}
			
			@SuppressWarnings("unchecked")
			Class<? extends MappingCacheStore> dynamicMappingCacheStore = (Class<? extends MappingCacheStore>) obj.getClass();
			MAPPING_CACHE_STORE_CLASS_MAP.put(dynamicMappingCacheStore.getName().toLowerCase(), dynamicMappingCacheStore);
			return (MappingCacheStore) obj;
		}
		return (MappingCacheStore) ConstructorUtil.newInstance(mappingCacheStoreClass);
	}
	
	
}
