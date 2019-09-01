package com.douglei.orm.configuration.impl.xml.element.environment.mapping;

import java.util.List;

import org.dom4j.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.configuration.environment.mapping.DynamicAddMappingException;
import com.douglei.orm.configuration.environment.mapping.DynamicCoverMappingException;
import com.douglei.orm.configuration.environment.mapping.DynamicRemoveMappingException;
import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.environment.mapping.MappingWrapper;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.context.xml.MappingXmlConfigContext;
import com.douglei.tools.instances.scanner.FileScanner;

/**
 * 
 * @author DougLei
 */
public class XmlMappingWrapper extends MappingWrapper{
	private static final Logger logger = LoggerFactory.getLogger(XmlMappingWrapper.class);
	
	public XmlMappingWrapper(EnvironmentProperty environmentProperty) {
		super(false, environmentProperty.getMappingStore());
	}
	public XmlMappingWrapper(boolean searchAll, List<Attribute> paths, DataSourceWrapper dataSourceWrapper, EnvironmentProperty environmentProperty) throws Exception {
		super(searchAll, environmentProperty.getMappingStore());
		
		if(initialMappingStore(environmentProperty.clearMappingStoreOnStart())) {
			FileScanner fileScanner = new FileScanner(MappingType.getMappingFileSuffixArray());
			scanMappingFiles(fileScanner, paths);
			List<String> list = fileScanner.getResult();
			if(list.size() > 0) {
				try {
					EnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
					for (String mappingConfigFilePath : list) {
						addMapping(XmlMappingFactory.newMappingInstance(mappingConfigFilePath));
					}
					MappingXmlConfigContext.executeCreateTable(dataSourceWrapper);
				} catch (Exception e) {
					throw e;
				} finally {
					fileScanner.destroy();
				}
			}
		}
	}
	
	@Override
	public String dynamicAddMapping(String mappingConfigurationFilePath) throws DynamicAddMappingException {
		try {
			logger.debug("dynamic add or cover mapping: {}", mappingConfigurationFilePath);
			return addOrCoverMapping(XmlMappingFactory.newMappingInstance(mappingConfigurationFilePath));
		} catch (Exception e) {
			throw new DynamicAddMappingException(e);
		}
	}
	
	@Override
	public String dynamicAddMapping(MappingType mappingType, String mappingConfigurationContent) throws DynamicAddMappingException {
		try {
			logger.debug("dynamic add or cover mapping: {}", mappingConfigurationContent);
			return addOrCoverMapping(XmlMappingFactory.newMappingInstance(mappingType, mappingConfigurationContent));
		} catch (Exception e) {
			throw new DynamicAddMappingException(e);
		}
	}
	
	@Override
	public String dynamicCoverMapping(String mappingConfigurationFilePath) throws DynamicCoverMappingException {
		try {
			logger.debug("dynamic add or cover ***simple*** mapping: {}", mappingConfigurationFilePath);
			return addOrCoverMapping_simple(XmlMappingFactory.newMappingInstance(mappingConfigurationFilePath));
		} catch (Exception e) {
			throw new DynamicCoverMappingException(e);
		}
	}
	
	@Override
	public String dynamicCoverMapping(MappingType mappingType, String mappingConfigurationContent) throws DynamicCoverMappingException {
		try {
			logger.debug("dynamic add or cover ***simple*** mapping: {}", mappingConfigurationContent);
			return addOrCoverMapping_simple(XmlMappingFactory.newMappingInstance(mappingType, mappingConfigurationContent));
		} catch (Exception e) {
			throw new DynamicCoverMappingException(e);
		}
	}
	
	@Override
	public void dynamicRemoveMapping(String mappingCode) throws DynamicRemoveMappingException {
		try {
			logger.debug("dynamic remove mapping-mappingCode: {}", mappingCode);
			removeMapping(mappingCode);
		} catch (Exception e) {
			throw new DynamicRemoveMappingException(e);
		}
	}
}
