package com.douglei.test.xmlreader;

import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DocumentReader {
	private static String xmlFilePath = "D:\\softwares\\developments\\workspaces\\jdb-orm\\src\\test\\resources\\mappings\\sql\\sql.smp.xml";
	private static String xmlFilePath2 = "D:\\softwares\\developments\\workspaces\\jdb-orm\\src\\test\\resources\\mappings\\sql\\sql.smp2.xml";
	
	@Test
	public void read() throws Exception {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		
		String name = Thread.currentThread().getName()+"===>";
		
		Document document = builder.parse(new FileInputStream(xmlFilePath));
		System.err.println("builder:" + builder);
		System.err.println("document:" + document);
		
		
		Element root = document.getDocumentElement();
		System.out.println(root.getNodeName());
		System.out.println(name + "type="+root.getAttribute("type"));
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList contentList = (NodeList) xpath.evaluate("sql/content", root, XPathConstants.NODESET);
		System.out.println(contentList.getLength());
		
		for(int i=0;i<contentList.getLength();i++) {
			Node node = contentList.item(i);
			
			
			NodeList children = node.getChildNodes();
			for(int j=0;j<children.getLength();j++) {
				Node n = children.item(j);
				System.out.println("--------------------");
				System.out.println(name + n.getTextContent());
				System.out.println("--------------------");
			}
			
		}
		document = builder.parse(new FileInputStream(xmlFilePath2));
		System.err.println("builder:" + builder);
		System.err.println("document:" + document);
	} 
}
