package com.douglei.test.xmlreader;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

public class Dom4jReader {
	private String xmlFilePath = "D:\\softwares\\developments\\workspaces\\jdb-orm\\src\\test\\resources\\mappings\\sql\\sql.smp.xml";
	
	@Test
	public void read() throws Exception {
		Document doc = new SAXReader().read(new File(xmlFilePath));
		List<?> nodes = doc.getRootElement().selectNodes("//sql/content");
		Node node = (Node) nodes.get(0);
		
		nodes = node.selectNodes("*");
		for (Object object : nodes) {
			System.out.println(((Node)object).asXML());
		}
		
	} 
}
