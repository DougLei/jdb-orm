package com.douglei.orm.configuration.environment.mapping.store;

import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.configuration.environment.mapping.store.impl.ApplicationMappingStore;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class MappingStoreMap {
	private static final Map<String, Class<? extends MappingStore>> MAPPING_STORE_CLASS_MAP = new HashMap<String, Class<? extends MappingStore>>(2);
	
	public static MappingStore getMappingStore(String type) {
		if(StringUtil.isEmpty(type) || type.equals("application") || type.equals(ApplicationMappingStore.class.getName())) {
			return new ApplicationMappingStore();
		}
		Class<? extends MappingStore> mappingStoreClass = MAPPING_STORE_CLASS_MAP.get(type);
		if(mappingStoreClass == null) {
			Object obj = null;
			try {
				obj = ConstructorUtil.newInstance(type);
			} catch (Exception e) {
				StringBuilder supportType = new StringBuilder(100);
				supportType.append("application, ").append(ApplicationMappingStore.class.getName());
				MAPPING_STORE_CLASS_MAP.keySet().forEach(key -> supportType.append(", ").append(key));
				throw new UnsupportMappingStoreException("系统目前不支持["+type+"], 目前支持的mappingStore值包括:"+supportType);
			}
			if(!(obj instanceof MappingStore)) {
				throw new UnsupportMappingStoreException("["+type+"]的类必须继承["+MappingStore.class.getName()+"]");
			}
			
			@SuppressWarnings("unchecked")
			Class<? extends MappingStore> dynamicMappingStore = (Class<? extends MappingStore>) obj.getClass();
			MAPPING_STORE_CLASS_MAP.put(dynamicMappingStore.getName(), dynamicMappingStore);
			return (MappingStore) obj;
		}
		return (MappingStore) ConstructorUtil.newInstance(mappingStoreClass);
	}
}
