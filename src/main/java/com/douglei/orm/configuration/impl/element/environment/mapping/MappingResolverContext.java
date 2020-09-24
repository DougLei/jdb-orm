package com.douglei.orm.configuration.impl.element.environment.mapping;

import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.dom4j.io.SAXReader;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.core.metadata.sql.content.ContentType;
import com.douglei.orm.core.metadata.sql.content.SqlContentMetadata;
import com.douglei.orm.core.metadata.validator.ValidateHandler;

/**
 * 映射解析器上下文
 * @author DougLei
 */
public class MappingResolverContext {
	private static final ThreadLocal<MappingResolver> resolverContext = new ThreadLocal<MappingResolver>();
	
	/**
	 * 获取解析器
	 * @return
	 */
	private static MappingResolver geResolver() {
		MappingResolver resolver = resolverContext.get();
		if(resolver == null) {
			resolver = new MappingResolver();
			resolverContext.set(resolver);
		}
		return resolver;
	}
	
	// -----------------------------------------------------------------------------
	// 获取xml阅读器实例
	// -----------------------------------------------------------------------------
	/**
	 * 获取SAXReader实例
	 * @return
	 */
	public static SAXReader getSAXReader() {
		return geResolver().getSAXReader();
	}
	
	/**
	 * 获取DocumentBuilder实例
	 * @return
	 * @throws ParserConfigurationException 
	 */
	public static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
		return geResolver().getDocumentBuilder();
	}
	
	
	// -----------------------------------------------------------------------------
	// SQL资源的处理
	// -----------------------------------------------------------------------------
	/**
	 * 读取sql映射时, 获取<validators>节点下<validator>子节点的集合
	 * @param sqlNode
	 * @return
	 * @throws XPathExpressionException 
	 */
	public static NodeList getValidatorNodeList(Node sqlNode) throws XPathExpressionException {
		return geResolver().getMappingResolver4Sql().getValidatorNodeList(sqlNode);
	}
	
	/**
	 * 读取sql映射时, 获取<content>节点的集合
	 * @param sqlNode
	 * @return
	 * @throws XPathExpressionException 
	 */
	public static NodeList getContentNodeList(Node sqlNode) throws XPathExpressionException {
		return geResolver().getMappingResolver4Sql().getContentNodeList(sqlNode);
	}
	
	/**
	 * 读取sql映射时, 获取<object>节点的集合
	 * @param sqlNode
	 * @return
	 * @throws XPathExpressionException 
	 */
	public static NodeList getObjectNodeList(Node sqlNode) throws XPathExpressionException {
		return geResolver().getMappingResolver4Sql().getObjectNodeList(sqlNode);
	}
	
	
	/**
	 * 解析sql映射时, 获取当前解析的sql的类型
	 * @return
	 */
	public static ContentType getCurrentSqlType() {
		return geResolver().getMappingResolver4Sql().getCurrentSqlType();
	}
	/**
	 * 解析sql映射时, 记录当前解析的sql的类型
	 * @param type
	 */
	public static void setCurrentSqlType(ContentType type) {
		geResolver().getMappingResolver4Sql().setCurrentSqlType(type);
	}
	
	
	/**
	 * 解析sql映射时, 获取配置的验证器集合
	 * @return
	 */
	public static Map<String, ValidateHandler> getSqlValidateHandlers() {
		return geResolver().getMappingResolver4Sql().getSqlValidateHandlers();
	}
	/**
	 * 解析sql映射时, 记录配置的验证器集合
	 * @param sqlValidateHandlers
	 */
	public static void setSqlValidateHandlers(Map<String, ValidateHandler> sqlValidateHandlers) {
		geResolver().getMappingResolver4Sql().setSqlValidateHandlers(sqlValidateHandlers);
	}
	
	
	/**
	 * 解析sql映射时, 根据sql-content的name, 获取对应sql-content的metadata实例
	 * @param sqlContentName
	 * @return 
	 */
	public static SqlContentMetadata getSqlContent(String sqlContentName) {
		return geResolver().getMappingResolver4Sql().getSqlContent(sqlContentName);
	}
	/**
	 * 解析sql映射时, 记录配置的sql-content集合
	 * @param sqlNode
	 * @throws XPathExpressionException 
	 * @throws RepeatedSqlContentNameException 
	 */
	public static void setSqlContents(Node sqlNode) throws XPathExpressionException {
		geResolver().getMappingResolver4Sql().setSqlContents(sqlNode);
	}
	/**
	 * 解析sql映射时, 判断是否存在指定name的sql-content的metadata实例
	 * @param sqlContentName
	 * @return
	 */
	public static boolean existsSqlContent(String sqlContentName) {
		return geResolver().getMappingResolver4Sql().existsSqlContent(sqlContentName);
	}
	
	// -----------------------------------------------------------------------------
	// 其他处理
	// -----------------------------------------------------------------------------
	/**
	 * 销毁
	 */
	public static void destroy() {
		resolverContext.remove();
	}
}
