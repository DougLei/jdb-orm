package com.douglei.orm.configuration.impl.xml.element.environment.mapping;

import java.util.List;

import org.dom4j.Attribute;

import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.environment.mapping.MappingStoreWrapper;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.context.xml.MappingXmlConfigContext;
import com.douglei.tools.instances.scanner.FileScanner;

/**
 * 
 * @author DougLei
 */
public class XmlMappingStoreWrapper extends MappingStoreWrapper{
	
	public XmlMappingStoreWrapper(EnvironmentProperty environmentProperty) {
		super(false, environmentProperty.getMappingStore());
	}
	
	public XmlMappingStoreWrapper(boolean searchAll, List<Attribute> paths, DataSourceWrapper dataSourceWrapper, EnvironmentProperty environmentProperty) throws Exception {
		super(searchAll, environmentProperty.getMappingStore());
		EnvironmentContext.setConfigurationEnvironmentProperty(environmentProperty);
		
		if(initialMappingStore(environmentProperty.clearMappingStoreOnStart())) {
			FileScanner fileScanner = new FileScanner(MappingType.getMappingFileSuffixArray());
			scanMappingFiles(fileScanner, paths);
			List<String> list = fileScanner.getResult();
			if(list.size() > 0) {
				try {
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
	
	/**
	 * 扫描映射文件
	 * @param fileScanner
	 * @param paths
	 */
	private void scanMappingFiles(FileScanner fileScanner, List<Attribute> paths) {
		if(paths != null) {
			StringBuilder path = new StringBuilder(paths.size() * 20);
			paths.forEach(p -> {
				path.append(",").append(p.getValue());
			});
			fileScanner.multiScan(searchAll, path.substring(1).split(","));
		}
	}
}
