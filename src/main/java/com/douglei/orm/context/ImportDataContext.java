package com.douglei.orm.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.tools.instances.scanner.FileScanner;

/**
 * 
 * @author DougLei
 */
public class ImportDataContext {
	private static final Logger logger = LoggerFactory.getLogger(ImportDataContext.class);
	private static final Map<String, Object> IMPORT_DATA = new HashMap<String, Object>(2);
	
	/**
	 * 获取导入的column元素集合
	 * @param importColumnFilePath
	 * @return
	 * @throws DocumentException 
	 */
	public static List<?> getImportColumnElements(String importColumnFilePath) throws DocumentException {
		Object importData = IMPORT_DATA.get(importColumnFilePath);
		if(importData == null) {
			List<String> files = new FileScanner().scan(true, importColumnFilePath);
			if(files.size() == 0) {
				throw new NullPointerException("在import-columns时, 未能在指定path=["+importColumnFilePath+"]下发现对应的配置文件");
			}
			if(files.size() > 1) {
				throw new MulitMatchImportPathException("在进行import-columns时, 根据指定path=["+importColumnFilePath+"], 扫描到多个相同的文件, 请检查:" + files.toString());
			}
			
			List<?> importDataList = new SAXReader().read(FileScanner.readByScanPath(files.get(0))).getRootElement().elements("column");
			if(importDataList == null || importDataList.size() == 0) {
				throw new NullPointerException("在指定path=["+importColumnFilePath+"]对应的配置文件中, 未能读取到任何<column>元素信息");
			}
			IMPORT_DATA.put(importColumnFilePath, importDataList);
			importData = importDataList;
		}
		logger.debug("从指定的path=[{}], 获取的import-columns集合为: {}", importColumnFilePath, importData);
		return (List<?>) importData;
	}
}
