package com.douglei.configuration.impl.xml.element.environment.mapping;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.mapping.DynamicAddMappingException;
import com.douglei.configuration.environment.mapping.MappingType;
import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.mapping.store.target.MappingStore;
import com.douglei.configuration.impl.xml.LocalXmlConfigurationSAXReader;
import com.douglei.instances.scanner.FileScanner;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class XmlMappingWrapper extends MappingWrapper{
	private static final Logger logger = LoggerFactory.getLogger(XmlMappingWrapper.class);
	
	public XmlMappingWrapper(MappingStore mappingStore) {
		super(mappingStore);
	}
	public XmlMappingWrapper(List<?> scanElements, List<?> pathElements, MappingStore mappingStore) throws Exception {
		super(mappingStore);
		
		FileScanner fileScanner = new FileScanner(MappingType.getFinalMappingFileSuffix());
		scanMappingFiles(fileScanner, scanElements, "base-path");
		scanMappingFiles(fileScanner, pathElements, "path");
		
		List<String> list = fileScanner.getResult();
		if(list.size() > 0) {
			try {
				initialMappingStoreSize(list.size());
				for (String mappingConfigFilePath : list) {
					addMapping(XmlMappingFactory.newInstanceByXml(mappingConfigFilePath, LocalXmlConfigurationSAXReader.getMappingSAXReader().read(new File(mappingConfigFilePath))));
				}
			} catch (Exception e) {
				throw e;
			} finally {
				fileScanner.destroy();
			}
		}else {
			initialMappingStoreSize(0);
		}
	}
	private void scanMappingFiles(FileScanner fileScanner, List<?> elements, String attributeName) {
		if(elements != null && elements.size() > 0) {
			String basePath = null;
			for (Object elem : elements) {
				basePath = ((Element)elem).attributeValue(attributeName);
				if(StringUtil.notEmpty(basePath)) {
					fileScanner.scan(basePath);
				}
			}
		}
	}
	
	@Override
	public void dynamicAddMapping(String mappingConfigurationContent) {
		try {
			logger.debug("dynamic add mapping: {}", mappingConfigurationContent);
			coverMapping(XmlMappingFactory.newInstanceByXml(mappingConfigurationContent, LocalXmlConfigurationSAXReader.getMappingSAXReader().read(new ByteArrayInputStream(mappingConfigurationContent.getBytes("utf-8")))));
		} catch (Exception e) {
			throw new DynamicAddMappingException("动态添加映射时出现异常", e);
		}
	}
}
