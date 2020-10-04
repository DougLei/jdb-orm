package com.douglei.orm.xmlreader;

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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.mapping.metadata.validator.ValidateHandler;
import com.douglei.tools.utils.StringUtil;

public class DocumentReader {
	private static String xmlFilePath = "D:\\workspace4\\jdb-orm\\src\\test\\resources\\mappings\\sql\\sql.smp.xml";
	
	@Test
	public void read() throws Exception {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document document = builder.parse(new FileInputStream(xmlFilePath));
		Element root = document.getDocumentElement();
		NodeList sqlNodeList = root.getElementsByTagName("sql");
		Node sqlNode = sqlNodeList.item(0);
		
		XPath xpath = XPathFactory.newInstance().newXPath();
//		XPathExpression expression = xpath.compile("content");
//		
//		NodeList nl = (NodeList) expression.evaluate(sqlNode, XPathConstants.NODESET);
//		Node contentNode = nl.item(1);
//		
//		NodeList children = contentNode.getChildNodes();
//		
//		int length = children.getLength();
//		System.out.println(length);
//		Node node = null;
//		for(int i=0;i<length;i++) {
//			node = children.item(i);
//			if("#text".equals(node.getNodeName())) {
//				System.out.println(node.getNodeName());
//				System.out.println(node.getNodeValue());
//			}
//		}
		
		
		
		XPathExpression expression = xpath.compile("validators/validator[@name!='']");
		
		NodeList validators = (NodeList) expression.evaluate(sqlNode, XPathConstants.NODESET);
		
		NamedNodeMap attributes = null;
		Node attribute = null;
		for(int i=0;i<validators.getLength();i++) {
			attributes = validators.item(i).getAttributes();
			String name = attributes.getNamedItem("name").getNodeValue();
			if(StringUtil.notEmpty(name)) {
				ValidateHandler handler = new ValidateHandler(name, true);
				if(attributes.getLength() > 1) {
					for(int j=0;j<attributes.getLength();j++) {
						attribute = attributes.item(j);
						if(!"name".equals(attribute.getNodeName())) {
							handler.addValidator(attribute.getNodeName(), attribute.getNodeValue());
						}
					}
				}
//				return handler;
				continue;
			}
			throw new NullPointerException("<validator>元素中的name属性值不能为空");
		}
		
	} 
}
