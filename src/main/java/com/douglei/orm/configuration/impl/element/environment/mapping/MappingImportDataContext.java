package com.douglei.orm.configuration.impl.element.environment.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.impl.util.Dom4jElementUtil;
import com.douglei.tools.instances.scanner.FileScanner;

/**
 * 映射导入的数据上下文
 * @author DougLei
 */
public class MappingImportDataContext {
	private static final Logger logger = LoggerFactory.getLogger(MappingImportDataContext.class);
	private static final Map<String, Object> IMPORT_DATA = new HashMap<String, Object>(4);
	
	/**
	 * 获取导入的column元素集合
	 * @param importColumnFilePath
	 * @return
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	public static List<Element> getImportColumnElements(String importColumnFilePath) throws DocumentException {
		Object importData = IMPORT_DATA.get(importColumnFilePath);
		if(importData == null) {
			List<String> files = new FileScanner().scan(importColumnFilePath);
			if(files.size() == 0) {
				throw new NullPointerException("在import-columns时, 未能在指定path=["+importColumnFilePath+"]下发现对应的配置文件");
			}
			
			List<Element> importDataList = Dom4jElementUtil.elements("column", new SAXReader().read(FileScanner.readByScanPath(files.get(0))).getRootElement());
			if(importDataList == null || importDataList.size() == 0) {
				throw new NullPointerException("在指定path=["+importColumnFilePath+"]对应的配置文件中, 未能读取到任何<column>元素信息");
			}
			IMPORT_DATA.put(importColumnFilePath, importDataList);
			importData = importDataList;
		}
		logger.debug("从指定的path=[{}], 获取的import-columns集合为: {}", importColumnFilePath, importData);
		return (List<Element>) importData;
	}
}
