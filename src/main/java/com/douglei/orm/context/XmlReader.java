package com.douglei.orm.context;

import javax.xml.parsers.DocumentBuilder;
import org.dom4j.io.SAXReader;
import javax.xml.xpath.XPathExpression;

/**
 * 
 * @author DougLei
 */
class XmlReader {
	SAXReader configurationSAXReader;// 读取xml configuration文件的SAXReader实例
	SAXReader tableMappingSAXReader;// 读取table-xml映射文件的SAXReader实例
	
	DocumentBuilder sqlMappingDocumentBuilder;// 读取sql-xml映射文件的DocumentBuilder实例
	XPathExpression contentNodeXPathExpression;// 读取sql-xml时, 获取<content>节点的表达式
}
