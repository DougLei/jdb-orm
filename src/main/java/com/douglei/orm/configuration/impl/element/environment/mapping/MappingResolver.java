package com.douglei.orm.configuration.impl.element.environment.mapping;

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

import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.SqlContentMetadataResolver;
import com.douglei.orm.core.metadata.sql.ContentType;
import com.douglei.orm.core.metadata.sql.SqlContentMetadata;
import com.douglei.orm.core.metadata.validator.ValidateHandler;

/**
 * 映射解析器
 * @author DougLei
 */
class MappingResolver {
	private SAXReader tableMappingReader; // 表映射读取器
	
	private DocumentBuilder sqlMappingReader; // SQL映射读取器
	private XPathExpression validatorNodeExpression;// 读取sql映射时, 用来获取<validators>节点下<validator>子节点的表达式
	private XPathExpression sqlContentNodeExpression;// 读取sql映射时, 用来获取<sql-content>节点的表达式
	private XPathExpression contentNodeExpression;// 读取sql映射时, 用来获取<content>节点的表达式
	
	private ContentType currentSqlType; // 解析sql映射时, 记录当前解析的sql的类型
	private Map<String, ValidateHandler> sqlValidateHandlers;// 解析sql映射时, 记录配置的验证器集合
	private static SqlContentMetadataResolver sqlContentMetadataResolver = new SqlContentMetadataResolver(); // sql-content的解析器
	private Map<String, Object> sqlContents;// 解析sql映射时, 记录配置的<sql-content>集合, value为Node或SqlContentMetadata, 一开始为Node, 在第一次获取时进行解析, 并将解析的结果SqlContentMetadata替换Node
	
	// -----------------------------------------------------------------------------
	// 表资源的处理
	// -----------------------------------------------------------------------------
	/**
	 * @see MappingResolverContext#getTableMappingReader()
	 * @return
	 */
	public SAXReader getTableMappingReader() {
		if(tableMappingReader == null)
			tableMappingReader = new SAXReader();
		return tableMappingReader;
	}

	// -----------------------------------------------------------------------------
	// SQL资源的处理
	// -----------------------------------------------------------------------------
	/**
	 * @see MappingResolverContext#getSqlMappingReader()
	 * @return
	 * @throws ParserConfigurationException
	 */
	public DocumentBuilder getSqlMappingReader() throws ParserConfigurationException {
		if(sqlMappingReader == null)
			sqlMappingReader = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return sqlMappingReader;
	}

	/**
	 * @see MappingResolverContext#getValidatorNodeList(Node)
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
	 * @see MappingResolverContext#getContentNodeList(Node)
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
	 * @see MappingResolverContext#getCurrentSqlType()
	 * @return
	 */
	public ContentType getCurrentSqlType() {
		return currentSqlType;
	}
	/**
	 * @see MappingResolverContext#setCurrentSqlType(ContentType)
	 * @param type
	 * @return
	 */
	public void setCurrentSqlType(ContentType type) {
		this.currentSqlType = type;
	}

	
	/**
	 * @see MappingResolverContext#getSqlValidateHandlers()
	 * @return
	 */
	public Map<String, ValidateHandler> getSqlValidateHandlers() {
		return sqlValidateHandlers;
	}
	/**
	 * @see MappingResolverContext#setSqlValidateHandlers(Map)
	 * @param sqlValidateHandlers2
	 */
	public void setSqlValidateHandlers(Map<String, ValidateHandler> sqlValidateHandlers) {
		this.sqlValidateHandlers = sqlValidateHandlers;
	}

	
	/**
	 * @see MappingResolverContext#getSqlContent(String)
	 * @param sqlContentName
	 * @return
	 */
	public SqlContentMetadata getSqlContent(String sqlContentName) {
		if(sqlContents != null && !sqlContents.isEmpty()) {
			Object sqlContentObj = sqlContents.get(sqlContentName);
			if(sqlContentObj instanceof Node) {
				sqlContentObj = sqlContentMetadataResolver.resolving((Node)sqlContentObj);
				sqlContents.put(sqlContentName, sqlContentObj);
			}
			return (SqlContentMetadata) sqlContentObj;
		}
		return null;
	}
	/**
	 * @see MappingResolverContext#setSqlContents(Node)
	 * @param sqlNode
	 * @throws XPathExpressionException 
	 * @throws RepeatedSqlContentNameException 
	 */
	public void setSqlContents(Node sqlNode) throws XPathExpressionException, RepeatedSqlContentNameException {
		if(sqlContentNodeExpression == null)
			sqlContentNodeExpression = XPathFactory.newInstance().newXPath().compile("sql-content[@name!='']");
		if(sqlContents != null)
			sqlContents.clear();
		
		NodeList sqlContentNodeList = (NodeList) sqlContentNodeExpression.evaluate(sqlNode, XPathConstants.NODESET);
		if(sqlContentNodeList != null && sqlContentNodeList.getLength() > 0) {
			if(sqlContents == null)
				sqlContents = new HashMap<String, Object>(8);
			
			Node node;
			String name;
			for (int i=0;i<sqlContentNodeList.getLength();i++) {
				node = sqlContentNodeList.item(i);
				name = sqlContentMetadataResolver.getName(node.getAttributes().getNamedItem("name"));
				if(sqlContents.containsKey(name))
					throw new RepeatedSqlContentNameException(name);
				sqlContents.put(name, node);
			}
			
		}
	}
	/**
	 * @see MappingResolverContext#existsSqlContent(String)
	 * @param sqlContentName
	 * @return
	 */
	public boolean existsSqlContent(String sqlContentName) {
		return sqlContents.get(sqlContentName) instanceof SqlContentMetadata;
	}
}
