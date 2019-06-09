package com.douglei.orm.xmlreader;

import java.io.File;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

public class Dom4jReader {
	private String xmlFilePath = "D:\\workspace4\\jdb-orm\\src\\test\\resources\\mappings\\table\\SysUser_class.tmp.xml";
	
	@Test
	public void read() throws Exception {
		Document doc = new SAXReader().read(new File(xmlFilePath));
		Element elem = doc.getRootElement().element("table").element("constraints").element("constraint");
		
		List<?> list = elem.selectNodes("column-name/@value");
		for (Object object : list) {
			System.out.println(((Attribute)object).getValue());
		}
	} 
}
