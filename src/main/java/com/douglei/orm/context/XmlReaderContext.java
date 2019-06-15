package com.douglei.orm.context;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.dom4j.io.SAXReader;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * xml reader上下文
 * @author DougLei
 */
public class XmlReaderContext {
	private static final ThreadLocal<XmlReader> XML_READER = new ThreadLocal<XmlReader>();
	private static XmlReader getXmlReader() {
		XmlReader xmlReader = XML_READER.get();
		if(xmlReader == null) {
			xmlReader = new XmlReader();
			XML_READER.set(xmlReader);
		}
		return xmlReader;
	}
	
	/**
	 * 获取 读取xml configuration文件的SAXReader实例 
	 * @return
	 */
	public static SAXReader getConfigurationSAXReader() {
		XmlReader xmlReader = getXmlReader();
		if(xmlReader.configurationSAXReader == null) {
			xmlReader.configurationSAXReader = new SAXReader();
		}
		return xmlReader.configurationSAXReader;
	}
	
	/**
	 * 获取 读取table-xml映射文件的SAXReader实例
	 * @return
	 */
	public static SAXReader getTableMappingReader() {
		XmlReader xmlReader = getXmlReader();
		if(xmlReader.tableMappingSAXReader == null) {
			xmlReader.tableMappingSAXReader = new SAXReader();
		}
		return xmlReader.tableMappingSAXReader;
	}
	
	/**
	 * 获取 读取sql-xml映射文件的DocumentBuilder实例
	 * @return
	 */
	public static DocumentBuilder getSqlMappingReader() {
		XmlReader xmlReader = getXmlReader();
		if(xmlReader.sqlMappingDocumentBuilder == null) {
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				xmlReader.sqlMappingDocumentBuilder = factory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				throw new BuildDocumentBuilderInstanceException(e);
			}
		}
		return xmlReader.sqlMappingDocumentBuilder;
	}
	
	/**
	 * 获取 读取sql-xml时, 获取<content>节点的表达式
	 * @param sqlNode
	 * @return
	 */
	public static NodeList getContentNodeList(Node sqlNode) {
		XmlReader xmlReader = getXmlReader();
		try {
			if(xmlReader.contentNodeXPathExpression == null) {
				xmlReader.contentNodeXPathExpression = XPathFactory.newInstance().newXPath().compile("content");
			}
			return (NodeList) xmlReader.contentNodeXPathExpression.evaluate(sqlNode, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			throw new GetSqlContentNodeListException(e);
		}
	}
}