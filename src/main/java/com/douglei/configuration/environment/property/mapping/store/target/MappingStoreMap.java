package com.douglei.configuration.environment.property.mapping.store.target;

import java.util.HashMap;
import java.util.Map;

import com.douglei.configuration.environment.property.mapping.store.target.impl.ApplicationMappingStore;
import com.douglei.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class MappingStoreMap {
	private static final Map<String, Class<? extends MappingStore>> MAPPING_STORE_CLASS_MAP = new HashMap<String, Class<? extends MappingStore>>(4);
	static {
		MAPPING_STORE_CLASS_MAP.put("application", ApplicationMappingStore.class);
		// TODO 后续可以集成其他缓存, 存储映射信息
	}
	
	public static MappingStore getMappingStore(String type) {
		type = type.toLowerCase();
		Class<? extends MappingStore> mappingStoreClass = MAPPING_STORE_CLASS_MAP.get(type);
		if(mappingStoreClass == null) {
			Object obj = null;
			try {
				obj = ConstructorUtil.newInstance(type);
			} catch (Exception e) {
				throw new UnsupportMappingStoreException("系统目前不支持["+type+"], 目前支持的mappingStore值包括:"+MAPPING_STORE_CLASS_MAP.keySet());
			}
			if(!(obj instanceof MappingStore)) {
				throw new UnsupportMappingStoreException("["+type+"]的类必须继承["+MappingStore.class.getName()+"]");
			}
			
			@SuppressWarnings("unchecked")
			Class<? extends MappingStore> dynamicMappingStore = (Class<? extends MappingStore>) obj.getClass();
			MAPPING_STORE_CLASS_MAP.put(dynamicMappingStore.getName().toLowerCase(), dynamicMappingStore);
			return (MappingStore) obj;
		}
		return (MappingStore) ConstructorUtil.newInstance(mappingStoreClass);
	}
	
	
}
