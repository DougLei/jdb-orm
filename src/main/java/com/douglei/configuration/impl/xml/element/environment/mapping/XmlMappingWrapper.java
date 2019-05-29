package com.douglei.configuration.impl.xml.element.environment.mapping;

import java.util.List;

import org.dom4j.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.mapping.DynamicAddMappingException;
import com.douglei.configuration.environment.mapping.MappingType;
import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.mapping.cache.store.MappingCacheStore;
import com.douglei.instances.scanner.FileScanner;

/**
 * 
 * @author DougLei
 */
public class XmlMappingWrapper extends MappingWrapper{
	private static final Logger logger = LoggerFactory.getLogger(XmlMappingWrapper.class);
	
	public XmlMappingWrapper(MappingCacheStore mappingCacheStore) {
		super(mappingCacheStore);
	}
	public XmlMappingWrapper(List<?> paths, MappingCacheStore mappingCacheStore) throws Exception {
		super(mappingCacheStore);
		
		FileScanner fileScanner = new FileScanner(MappingType.getMappingFileSuffixArray());
		scanMappingFiles(fileScanner, paths);
		
		List<String> list = fileScanner.getResult();
		if(list.size() > 0) {
			try {
				initialMappingCacheStoreSize(list.size());
				for (String mappingConfigFilePath : list) {
					addMapping(XmlMappingFactory.newInstance_initial(mappingConfigFilePath));
				}
			} catch (Exception e) {
				throw e;
			} finally {
				fileScanner.destroy();
			}
		}else {
			initialMappingCacheStoreSize(0);
		}
	}
	private void scanMappingFiles(FileScanner fileScanner, List<?> elements) {
		if(elements != null && elements.size() > 0) {
			for (Object elem : elements) {
				fileScanner.scan(((Attribute)elem).getValue());
			}
		}
	}
	
	@Override
	public void dynamicAddMapping(MappingType mappingType, String mappingConfigurationContent) {
		try {
			logger.debug("dynamic add mapping: {}", mappingConfigurationContent);
			coverMapping(XmlMappingFactory.newInstance_dynamicAdd(mappingType, mappingConfigurationContent));
		} catch (Exception e) {
			throw new DynamicAddMappingException("动态添加映射时出现异常", e);
		}
	}
}
