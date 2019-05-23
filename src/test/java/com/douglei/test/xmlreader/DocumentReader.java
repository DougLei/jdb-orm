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
		
		Document document = builder.parse(new FileInputStream(xmlFilePath));
		Element root = document.getDocumentElement();
		NodeList sqlNodeList = root.getElementsByTagName("sql");
		Node sqlNode = sqlNodeList.item(0);
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expression = xpath.compile("content");
		
		NodeList nl = (NodeList) expression.evaluate(sqlNode, XPathConstants.NODESET);
		Node contentNode = nl.item(0);
		
		NodeList children = contentNode.getChildNodes();
		
		int length = children.getLength();
		Node node = null;
		for(int i=0;i<length;i++) {
			node = children.item(i);
			if("trim".equals(node.getNodeName())) {
				System.out.println(node.getNodeName());
				System.out.println(node.getTextContent());
				System.out.println(node.getAttributes().getNamedItem("xxx"));
			}
		}
		
	} 
}
