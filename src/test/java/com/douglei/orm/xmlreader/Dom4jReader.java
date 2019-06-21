package com.douglei.orm.xmlreader;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.douglei.orm.configuration.impl.xml.util.Dom4jElementUtil;

public class Dom4jReader {
	private String xmlFilePath = "D:\\workspace4\\jdb-orm\\src\\test\\resources\\mappings\\table\\SysUser_class.tmp.xml";
	
	@Test
	public void read() throws Exception {
		Document doc = new SAXReader().read(new File(xmlFilePath));
		Element elem = doc.getRootElement().element("table").element("indexes").element("index");
		
//		List<?> list = elem.selectNodes("column-name/@value");
//		for (Object object : list) {
//			System.out.println(((Attribute)object).getValue());
//		}
		
		
		System.out.println(elem.attributeValue("type") == null);
		
		elem = Dom4jElementUtil.validateElementExists("createSql", elem);
		System.out.println("text trim----------------------------");
		System.out.println(elem.getTextTrim());
		System.out.println("string value----------------------------");
		System.out.println(elem.getStringValue());
		
		
		
		
	} 
}
