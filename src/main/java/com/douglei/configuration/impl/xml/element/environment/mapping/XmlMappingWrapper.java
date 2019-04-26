package com.douglei.configuration.impl.xml.element.environment.mapping;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.DestroyException;
import com.douglei.configuration.SelfCheckingException;
import com.douglei.configuration.environment.mapping.Mapping;
import com.douglei.configuration.environment.mapping.MappingType;
import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.instances.scanner.FileScanner;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class XmlMappingWrapper extends MappingWrapper{
	private static final Logger logger = LoggerFactory.getLogger(XmlMappingWrapper.class);
	
	
	public XmlMappingWrapper() {
		initialDefaultMappingsMap();
	}
	public XmlMappingWrapper(List<?> scanElements, List<?> pathElements) throws Exception {
		FileScanner fileScanner = new FileScanner(MappingType.getFinalMappingFileSuffix());
		scanMappingFiles(fileScanner, scanElements, "base-path");
		scanMappingFiles(fileScanner, pathElements, "path");
		
		List<String> list = fileScanner.getResult();
		if(list.size()>0) {
			mappings = new HashMap<String, Mapping>(list.size());
			try {
				SAXReader reader = new SAXReader();
				for (String mappingConfigFilePath : list) {
					addMapping(XmlMappingFactory.newInstanceByXml(mappingConfigFilePath, reader.read(new File(mappingConfigFilePath))));
				}
			} catch (Exception e) {
				throw e;
			} finally {
				fileScanner.destroy();
			}
		}else {
			initialDefaultMappingsMap();
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
	public void doDestroy() throws DestroyException {
		logger.debug("{} 开始 destroy", getClass());
		if(mappings != null && mappings.size() > 0) {
			mappings.clear();
		}
		logger.debug("{} 结束 destroy", getClass());
	}

	@Override
	public void selfChecking() throws SelfCheckingException {
	}
}
