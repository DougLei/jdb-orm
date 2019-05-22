package com.douglei.test.xmlreader;

import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DocumentReader {
	private static String xmlFilePath = "D:\\softwares\\developments\\workspaces\\jdb-orm\\src\\test\\resources\\mappings\\sql\\sql.smp.xml";
	
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
		
		NodeList sqlNodeList = root.getElementsByTagName("sql");
		System.out.println(sqlNodeList.getLength());
		Node sqlNode = sqlNodeList.item(0);
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expression = xpath.compile("content");
		
		NodeList nl = (NodeList) expression.evaluate(sqlNode, XPathConstants.NODESET);
		Node contentNode = nl.item(0);
		
		NodeList children = contentNode.getChildNodes();
		System.out.println(children.getLength());
		
		int length = children.getLength();
		Node node = null;
		for(int i=0;i<length;i++) {
			node = children.item(i);
			if(node.getNodeType() == Node.COMMENT_NODE) {
				continue;
			}
			
			System.out.println("----------------------------------------------------------------------------------------------");
			System.out.println(node.getNodeName() + "\t"+node.getNodeName().equals("if") + "\t" + node.getNodeType() + "\t" + node.getNodeValue());
		}
		
	} 
}
