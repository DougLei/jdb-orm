package com.douglei.orm.xmlreader;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

public class Dom4jReader {
	private String xmlFilePath = "D:\\workspace4\\jdb-orm\\src\\test\\resources\\mappings\\table\\SysUser_class.tmp.xml";
	
	@Test
	public void read() throws Exception {
		FileInputStream fis = new FileInputStream(new File(xmlFilePath));
		Document doc = new SAXReader().read(fis);
		
//		Element elem = doc.getRootElement().element("table").element("indexes").element("index");
		
		List<?> elems = doc.getRootElement().element("table").element("columns").elements("column");
		System.out.println(elems);
		if(elems != null) {
			System.out.println(elems.size());
			for (Object object : elems) {
				System.out.println(((Element)object).attributeValue("name"));
			}
		}else {
			System.out.println("elems is null");
		}
		
//		List<?> list = elem.selectNodes("column-name/@value");
//		for (Object object : list) {
//			System.out.println(((Attribute)object).getValue());
//		}
		
		
//		System.out.println(elem.attributeValue("type") == null);
//		
//		elem = Dom4jElementUtil.validateElementExists("createSql", elem);
//		System.out.println("text trim----------------------------");
//		System.out.println(elem.getTextTrim());
//		System.out.println("string value----------------------------");
//		System.out.println(elem.getStringValue());
//		
//		System.out.println(doc.getRootElement().element("table").element("import-columns").attributeValue("path"));
//		
//		@SuppressWarnings("resource")
//		String a = new Scanner(System.in).next();
//		System.out.println(a);
	} 
}
