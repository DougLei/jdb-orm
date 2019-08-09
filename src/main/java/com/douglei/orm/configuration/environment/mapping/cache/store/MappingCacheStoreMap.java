package com.douglei.orm.configuration.environment.mapping.cache.store;

import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.configuration.environment.mapping.cache.store.impl.ApplicationMappingCacheStore;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class MappingCacheStoreMap {
	private static final Map<String, Class<? extends MappingCacheStore>> MAPPING_CACHE_STORE_CLASS_MAP = new HashMap<String, Class<? extends MappingCacheStore>>(2);
	
	public static MappingCacheStore getMappingCacheStore(String type) {
		if(StringUtil.isEmpty(type)) {
			return new ApplicationMappingCacheStore();
		}
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
			MAPPING_CACHE_STORE_CLASS_MAP.put(dynamicMappingCacheStore.getName(), dynamicMappingCacheStore);
			return (MappingCacheStore) obj;
		}
		return (MappingCacheStore) ConstructorUtil.newInstance(mappingCacheStoreClass);
	}
	
	
}
