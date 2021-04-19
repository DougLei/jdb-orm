package com.douglei.orm.mapping;

import java.util.HashMap;
import java.util.Map;

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

import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.impl.sql.parser.content.SqlContentMetadataParser;
import com.douglei.orm.mapping.metadata.MetadataParseException;

/**
 * 
 * @author DougLei
 */
public class MappingParseTool {
	private SAXReader saxReader;
	private DocumentBuilder documentBuilder;
	private ContentType sqlContentType; // 解析sql映射时, 记录当前解析的sql的ContentType
	private XPathExpression validatorNodeExpression;// 解析sql映射时, 用来获取validators下validator集合的表达式
	private XPathExpression parameterNodeExpression;// 解析sql-query映射时, 用来获取parameters下parameter集合的表达式
	private XPathExpression contentNodeExpression;// 解析sql/sql-query映射时, 用来获取content集合的表达式
	private static SqlContentMetadataParser sqlContentMetadataParser = new SqlContentMetadataParser(); // sql-content的解析器
	private XPathExpression sqlContentNodeExpression;// 解析sql映射时, 用来获取sql-content集合的表达式
	private Map<String, Object> sqlContentMap;// 解析sql映射时, 记录sql-content的Map集合, value为Node或SqlContentMetadata, 一开始为Node, 在第一次获取时进行解析, 并将解析的结果SqlContentMetadata覆盖Node
	
	/**
	 * 获取SAXReader实例(Dom4j读取XML)
	 * @return
	 */
	public SAXReader getSAXReader() {
		if(saxReader == null)
			saxReader = new SAXReader();
		return saxReader;
	}
	
	/**
	 * 获取DocumentBuilder实例(DocumentBuilder读取XML) 
	 * @return
	 * @throws ParserConfigurationException 
	 */
	public DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
		if(documentBuilder == null)
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return documentBuilder;
	}
	
	/**
	 * 解析sql映射时, 记录当前解析的sql的ContentType
	 * @param type
	 * @return
	 */
	public void setCurrentSqlContentType(ContentType type) {
		this.sqlContentType = type;
	}
	/**
	 * 解析sql映射时, 获取当前解析的sql的ContentType
	 * @return
	 */
	public ContentType getCurrentSqlContentType() {
		return sqlContentType;
	}
	
	/**
	 * 解析sql映射时, 获取validators下validator集合
	 * @param sqlNode
	 * @return
	 * @throws XPathExpressionException 
	 */
	public NodeList getValidatorNodeList(Node sqlNode) throws XPathExpressionException {
		if(validatorNodeExpression == null)
			validatorNodeExpression = XPathFactory.newInstance().newXPath().compile("validators/validator[@name!='']");
		return (NodeList) validatorNodeExpression.evaluate(sqlNode, XPathConstants.NODESET);
	}
	
	/**
	 * 解析sql-query映射时, 获取parameters下parameter集合
	 * @param sqlNode
	 * @return
	 * @throws XPathExpressionException 
	 */
	public NodeList getParameterNodeList(Node sqlNode) throws XPathExpressionException {
		if(parameterNodeExpression == null)
			parameterNodeExpression = XPathFactory.newInstance().newXPath().compile("parameters/parameter[@name!='']");
		return (NodeList) parameterNodeExpression.evaluate(sqlNode, XPathConstants.NODESET);
	}
	
	/**
	 * 解析sql/sql-query映射时, 获取content集合
	 * @param sqlNode
	 * @return
	 * @throws XPathExpressionException 
	 */
	public NodeList getContentNodeList(Node sqlNode) throws XPathExpressionException {
		if(contentNodeExpression == null)
			contentNodeExpression = XPathFactory.newInstance().newXPath().compile("content");
		return (NodeList) contentNodeExpression.evaluate(sqlNode, XPathConstants.NODESET);
	}
	
	/**
	 * 解析sql映射时, 记录sql-content的Map集合
	 * @param sqlNode
	 * @return
	 * @throws XPathExpressionException
	 * 
	 */
	public boolean setSqlContentMap(Node sqlNode) throws XPathExpressionException {
		if(sqlContentNodeExpression == null)
			sqlContentNodeExpression = XPathFactory.newInstance().newXPath().compile("sql-content[@name!='']");
		if(sqlContentMap != null)
			sqlContentMap.clear();
		
		NodeList sqlContentNodeList = (NodeList) sqlContentNodeExpression.evaluate(sqlNode, XPathConstants.NODESET);
		if(sqlContentNodeList.getLength() == 0) 
			return false;
		
		if(sqlContentMap == null)
			sqlContentMap = new HashMap<String, Object>();
		
		for (int i=0;i<sqlContentNodeList.getLength();i++) {
			Node node = sqlContentNodeList.item(i);
			String name = sqlContentMetadataParser.getName(node, node.getAttributes().getNamedItem("name"));
			if(sqlContentMap.containsKey(name))
				throw new MetadataParseException("重复配置了name为"+name+"的<sql-content>");
			sqlContentMap.put(name, node);
		}
		return true;
	}
	/**
	 * 解析sql映射时, 获取指定name的SqlContentMetadata实例
	 * @param name
	 * @return
	 */
	public SqlContentMetadata getSqlContent(String name) {
		if(sqlContentMap == null || sqlContentMap.size() == 0)
			return null;
		
		Object obj = sqlContentMap.get(name);
		if(obj == null)
			return null;
		
		if(obj instanceof Node) {
			SqlContentMetadata content = sqlContentMetadataParser.parse((Node)obj);
			sqlContentMap.put(name, content);
			return content;
		}
		return (SqlContentMetadata) obj;
	}
	
	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<String, String>();
		
		System.out.println(map.put("1", "douglei"));
		System.out.println(map.put("1", "金石磊"));
		
	}
	
	
	/**
	 * 解析sql映射时, 获取sql-content的Map集合
	 * @return
	 */
	public Map<String, Object> getSqlContentMap() {
		return sqlContentMap;
	}
}