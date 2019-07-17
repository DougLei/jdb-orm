package com.douglei.orm.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.douglei.tools.instances.scanner.FileScanner;

/**
 * 
 * @author DougLei
 */
public class ImportDataContext {
	private static final Map<String, Object> IMPORT_DATA = new HashMap<String, Object>(2);
	
	/**
	 * 获取导入的column元素集合
	 * @param importColumnFilePath
	 * @return
	 * @throws DocumentException 
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> getImportColumnElements(String importColumnFilePath) throws DocumentException {
		Object importData = IMPORT_DATA.get(importColumnFilePath);
		if(importData == null) {
			List<String> files = new FileScanner().scan(true, importColumnFilePath);
			if(files.size() == 0) {
				return null;
			}
			if(files.size() > 1) {
				throw new MulitMatchImportColumnPathException(importColumnFilePath, files);
			}
			
			List<Object> importDataList = new SAXReader().read(FileScanner.readByScanPath(files.get(0))).getRootElement().elements("column");
			if(importDataList == null || importDataList.size() == 0) {
				return null;
			}
			IMPORT_DATA.put(importColumnFilePath, importDataList);
			return importDataList;
		}
		return (List<Object>) importData;
	}
}
