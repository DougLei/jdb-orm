package com.douglei.orm.context;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.dom4j.io.SAXReader;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author DougLei
 */
public class MappingXmlReaderContext {
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
	 * 读取sql-xml时, 获取<validators>节点下<validator>子节点
	 * @param sqlNode
	 * @return
	 */
	public static NodeList getValidatorNodeList(Node sqlNode) {
		XmlReader xmlReader = getXmlReader();
		try {
			if(xmlReader.validatorNodeXPathExpression == null) {
				xmlReader.validatorNodeXPathExpression = XPathFactory.newInstance().newXPath().compile("validators/validator[@name!='']");
			}
			return (NodeList) xmlReader.validatorNodeXPathExpression.evaluate(sqlNode, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			throw new DocumentBuilderException("获取<sql>-<validators>下<validator>元素的NodeList时出现异常", e);
		}
	}
	
	/**
	 * 读取sql-xml时, 获取<content>节点
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
			throw new DocumentBuilderException("获取<sql>下<content>元素的NodeList时出现异常", e);
		}
	}
	
	// -----------------------------------------------------------------------------------------
	/**
	 * 销毁
	 */
	public static void destroy() {
		XML_READER.remove();
		MappingConfigContext.destroy();
	}
}

/**
 * 
 * @author DougLei
 */
class XmlReader {
	SAXReader configurationSAXReader;// 读取xml configuration文件的SAXReader实例
	SAXReader tableMappingSAXReader;// 读取table-xml映射文件的SAXReader实例
	
	DocumentBuilder sqlMappingDocumentBuilder;// 读取sql-xml映射文件的DocumentBuilder实例
	XPathExpression validatorNodeXPathExpression;// 读取sql-xml时,获取<validators>节点下<validator>子节点的表达式
	XPathExpression contentNodeXPathExpression;// 读取sql-xml时, 获取<content>节点的表达式
}

/**
 * 
 * @author DougLei
 */
class BuildDocumentBuilderInstanceException extends RuntimeException{
	private static final long serialVersionUID = 2685054482425871566L;
	public BuildDocumentBuilderInstanceException(Throwable cause) {
		super("创建"+DocumentBuilder.class.getName()+"实例时出现异常", cause);
	}
}

/**
 * 
 * @author DougLei
 */
class DocumentBuilderException extends RuntimeException{
	private static final long serialVersionUID = -2367902688769800990L;
	public DocumentBuilderException(String message, Throwable cause) {
		super(message, cause);
	}
}