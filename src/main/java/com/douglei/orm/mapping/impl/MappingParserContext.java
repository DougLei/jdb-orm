package com.douglei.orm.mapping.impl;

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
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.SqlContentMetadataParser;
import com.douglei.orm.mapping.metadata.MetadataParseException;

/**
 * 映射解析器上下文
 * @author DougLei
 */
public class MappingParserContext {
	private static final ThreadLocal<MappingParser> parserContext = new ThreadLocal<MappingParser>();
	
	/**
	 * 获取解析器
	 * @return
	 */
	private static MappingParser geParser() {
		MappingParser parser = parserContext.get();
		if(parser == null) {
			parser = new MappingParser();
			parserContext.set(parser);
		}
		return parser;
	}
	
	// -----------------------------------------------------------------------------
	// 获取xml阅读器实例
	// -----------------------------------------------------------------------------
	/**
	 * 获取SAXReader实例
	 * @return
	 */
	public static SAXReader getSAXReader() {
		return geParser().getSAXReader();
	}
	
	/**
	 * 获取DocumentBuilder实例
	 * @return
	 * @throws ParserConfigurationException 
	 */
	public static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
		return geParser().getDocumentBuilder();
	}
	
	
	// -----------------------------------------------------------------------------
	// SQL资源的处理
	// -----------------------------------------------------------------------------
	/**
	 * 读取sql映射时, 获取validators下validator集合
	 * @param sqlNode
	 * @return
	 * @throws XPathExpressionException 
	 */
	public static NodeList getValidatorNodeList(Node sqlNode) throws XPathExpressionException {
		return geParser().getMappingParserSql().getValidatorNodeList(sqlNode);
	}
	
	/**
	 * 读取sql映射时, 获取content的集合
	 * @param sqlNode
	 * @return
	 * @throws XPathExpressionException 
	 */
	public static NodeList getContentNodeList(Node sqlNode) throws XPathExpressionException {
		return geParser().getMappingParserSql().getContentNodeList(sqlNode);
	}
	

	/**
	 * 解析sql映射时, 获取当前解析的sql的类型
	 * @return
	 */
	public static ContentType getCurrentSqlType() {
		return geParser().getMappingParserSql().getCurrentSqlType();
	}
	/**
	 * 解析sql映射时, 记录当前解析的sql的类型
	 * @param type
	 */
	public static void setCurrentSqlType(ContentType type) {
		geParser().getMappingParserSql().setCurrentSqlType(type);
	}
	
	
	/**
	 * 解析sql映射时, 记录配置的sql-content集合
	 * @param sqlNode
	 * @throws XPathExpressionException 
	 * @throws  
	 */
	public static void setSqlContents(Node sqlNode) throws XPathExpressionException {
		geParser().getMappingParserSql().setSqlContents(sqlNode);
	}
	/**
	 * 解析sql映射时, 根据sql-content的name, 获取对应sql-content的metadata实例
	 * @param sqlContentName
	 * @return 
	 */
	public static SqlContentMetadata getSqlContent(String sqlContentName) {
		return geParser().getMappingParserSql().getSqlContent(sqlContentName);
	}
	/**
	 * 解析sql映射时, 判断是否存在指定name的sql-content的metadata实例
	 * @param sqlContentName
	 * @return
	 */
	public static boolean existsSqlContent(String sqlContentName) {
		return geParser().getMappingParserSql().existsSqlContent(sqlContentName);
	}
	
	// -----------------------------------------------------------------------------
	// 其他处理
	// -----------------------------------------------------------------------------
	/**
	 * 销毁
	 */
	public static void destroy() {
		parserContext.remove();
	}
}

/**
 * 
 * @author DougLei
 */
class MappingParser {
	private SAXReader saxReader; // SAXReader
	private DocumentBuilder documentBuilder; // DocumentBuilder
	
	private MappingParser4Sql mappingParserSql; // sql映射专用处理器
	
	/**
	 * @see MappingParserContext#getSAXReader()
	 * @return
	 */
	public SAXReader getSAXReader() {
		if(saxReader == null)
			saxReader = new SAXReader();
		return saxReader;
	}
	
	/**
	 * @see MappingParserContext#getDocumentBuilder()
	 * @return
	 * @throws ParserConfigurationException 
	 */
	public DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
		if(documentBuilder == null)
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return documentBuilder;
	}
	
	/**
	 * 获取sql映射专用处理器
	 * @return
	 */
	public MappingParser4Sql getMappingParserSql() {
		if(mappingParserSql == null)
			mappingParserSql = new MappingParser4Sql();
		return mappingParserSql;
	}
}

/**
 * sql映射解析器
 * @author DougLei
 */
class MappingParser4Sql {
	private XPathExpression validatorNodeExpression;// 读取sql映射时, 用来获取<validators>节点下<validator>子节点的表达式
	private XPathExpression sqlContentNodeExpression;// 读取sql映射时, 用来获取<sql-content>节点的表达式
	private XPathExpression contentNodeExpression;// 读取sql映射时, 用来获取<content>节点的表达式
	
	private ContentType currentSqlType; // 解析sql映射时, 记录当前解析的sql的类型
	
	private static SqlContentMetadataParser sqlContentMetadataParser = new SqlContentMetadataParser(); // sql-content的解析器
	private Map<String, Object> sqlContents;// 解析sql映射时, 记录配置的<sql-content>集合, value为Node或SqlContentMetadata, 一开始为Node, 在第一次获取时进行解析, 并将解析的结果SqlContentMetadata替换Node
	
	
	/**
	 * @see MappingParserContext#getValidatorNodeList(Node)
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
	 * @see MappingParserContext#getContentNodeList(Node)
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
	 * @see MappingParserContext#getCurrentSqlType()
	 * @return
	 */
	public ContentType getCurrentSqlType() {
		return currentSqlType;
	}
	/**
	 * @see MappingParserContext#setCurrentSqlType(ContentType)
	 * @param type
	 * @return
	 */
	public void setCurrentSqlType(ContentType type) {
		this.currentSqlType = type;
	}

	
	/**
	 * @see MappingParserContext#setSqlContents(Node)
	 * @param sqlNode
	 * @throws XPathExpressionException 
	 */
	public void setSqlContents(Node sqlNode) throws XPathExpressionException {
		if(sqlContentNodeExpression == null)
			sqlContentNodeExpression = XPathFactory.newInstance().newXPath().compile("sql-content[@name!='']");
		if(sqlContents != null)
			sqlContents.clear();
		
		NodeList sqlContentNodeList = (NodeList) sqlContentNodeExpression.evaluate(sqlNode, XPathConstants.NODESET);
		if(sqlContentNodeList.getLength() > 0) {
			if(sqlContents == null)
				sqlContents = new HashMap<String, Object>();
			
			for (int i=0;i<sqlContentNodeList.getLength();i++) {
				Node node = sqlContentNodeList.item(i);
				String name = sqlContentMetadataParser.getName(node, node.getAttributes().getNamedItem("name"));
				if(sqlContents.containsKey(name))
					throw new MetadataParseException("重复配置了name为"+name+"的<sql-content>");
				sqlContents.put(name, node);
			}
			
		}
	}
	/**
	 * @see MappingParserContext#getSqlContent(String)
	 * @param sqlContentName
	 * @return
	 */
	public SqlContentMetadata getSqlContent(String sqlContentName) {
		if(sqlContents == null || sqlContents.size() == 0)
			return null;
		
		Object obj = sqlContents.get(sqlContentName);
		if(obj == null)
			return null;
		
		if(obj instanceof Node) 
			return (SqlContentMetadata) sqlContents.put(sqlContentName, sqlContentMetadataParser.parse((Node)obj));
		return (SqlContentMetadata) obj;
	}
	/**
	 * @see MappingParserContext#existsSqlContent(String)
	 * @param sqlContentName
	 * @return
	 */
	public boolean existsSqlContent(String sqlContentName) {
		return sqlContents.get(sqlContentName) instanceof SqlContentMetadata;
	}
}
