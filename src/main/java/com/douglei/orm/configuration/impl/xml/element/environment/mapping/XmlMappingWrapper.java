package com.douglei.orm.configuration.impl.xml.element.environment.mapping;

import java.util.List;

import org.dom4j.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.configuration.environment.mapping.DynamicAddMappingException;
import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.environment.mapping.MappingWrapper;
import com.douglei.orm.configuration.environment.mapping.cache.store.MappingCacheStore;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.context.RunMappingConfigurationContext;
import com.douglei.tools.instances.scanner.FileScanner;

/**
 * 
 * @author DougLei
 */
public class XmlMappingWrapper extends MappingWrapper{
	private static final Logger logger = LoggerFactory.getLogger(XmlMappingWrapper.class);
	
	public XmlMappingWrapper(MappingCacheStore mappingCacheStore) {
		super(mappingCacheStore);
	}
	public XmlMappingWrapper(List<?> paths, DataSourceWrapper dataSourceWrapper, EnvironmentProperty environmentProperty) throws Exception {
		super(environmentProperty.getMappingCacheStore());
		
		FileScanner fileScanner = new FileScanner(MappingType.getMappingFileSuffixArray());
		scanMappingFiles(fileScanner, paths);
		
		List<String> list = fileScanner.getResult();
		if(list.size() > 0) {
			try {
				DBRunEnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
				initializeMappingCacheStore(list.size());
				for (String mappingConfigFilePath : list) {
					addMapping(XmlMappingFactory.newMappingInstance(mappingConfigFilePath));
				}
				RunMappingConfigurationContext.executeCreateTable(dataSourceWrapper);
			} catch (Exception e) {
				throw e;
			} finally {
				fileScanner.destroy();
			}
		}else {
			initializeMappingCacheStore(0);
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
	public String dynamicAddMapping(MappingType mappingType, String mappingConfigurationContent) {
		try {
			logger.debug("dynamic add or cover mapping: {}", mappingConfigurationContent);
			return addOrCoverMapping(XmlMappingFactory.newMappingInstance(mappingType, mappingConfigurationContent));
		} catch (Exception e) {
			throw new DynamicAddMappingException("动态添加映射时出现异常", e);
		}
	}
	
	@Override
	public String dynamicCoverMapping(MappingType mappingType, String mappingConfigurationContent) {
		try {
			logger.debug("dynamic add or cover ***simple*** mapping: {}", mappingConfigurationContent);
			return addOrCoverMapping_simple(XmlMappingFactory.newMappingInstance(mappingType, mappingConfigurationContent));
		} catch (Exception e) {
			throw new DynamicAddMappingException("动态覆盖映射时出现异常", e);
		}
	}
	
	@Override
	public void dynamicRemoveMapping(String mappingCode) {
		logger.debug("dynamic remove mapping-mappingCode: {}", mappingCode);
		removeMapping(mappingCode);
	}
}
