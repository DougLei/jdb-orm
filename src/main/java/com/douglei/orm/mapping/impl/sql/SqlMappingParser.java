package com.douglei.orm.mapping.impl.sql;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.mapping.MappingProperty;
import com.douglei.orm.mapping.MappingParser;
import com.douglei.orm.mapping.impl.MappingParserContext;
import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.parser.SqlMetadataParser;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.ContentMetadataParser;
import com.douglei.orm.mapping.metadata.parser.MetadataParseException;
import com.douglei.orm.mapping.metadata.validator.ValidateHandler;
import com.douglei.orm.mapping.metadata.validator.ValidatorContainer;
import com.douglei.orm.mapping.type.MappingTypeConstants;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
class SqlMappingParser extends MappingParser<SqlMapping>{
	private static SqlMetadataParser sqlMetadataParser = new SqlMetadataParser();
	private static ContentMetadataParser contentMetadataParser = new ContentMetadataParser();
	
	private SqlMetadata sqlMetadata;
	
	@Override
	public SqlMapping parse(InputStream input) throws Exception {
		Document sqlDocument = MappingParserContext.getDocumentBuilder().parse(input);
		Element rootElement = sqlDocument.getDocumentElement();
		
		Node sqlNode = getSqlNode(rootElement.getElementsByTagName(MappingTypeConstants.SQL));
		this.sqlMetadata = sqlMetadataParser.parse(sqlNode);
		
		setParameterValidator(sqlNode);
		MappingParserContext.setSqlContents(sqlNode);
		
		resolvingContents(sqlNode);
		
		return new SqlMapping(sqlMetadata, getSqlMappingPropertyByDocument(rootElement.getElementsByTagName("property")));
	}
	
	/**
	 * 获取唯一的<sql>元素
	 * @param sqlNodeList
	 * @return
	 */
	private Node getSqlNode(NodeList sqlNodeList) {
		if(sqlNodeList == null || sqlNodeList.getLength() == 0) 
			throw new MetadataParseException("没有配置<sql>元素");
		if(sqlNodeList.getLength() > 1) 
			throw new MetadataParseException("<sql>元素最多只能配置一个");
		return sqlNodeList.item(0);
	}
	
	/**
	 * 设置配置的参数验证器
	 * @param sqlNode
	 * @throws XPathExpressionException 
	 */
	private void setParameterValidator(Node sqlNode) throws XPathExpressionException {
		Map<String, ValidateHandler> validateHandlerMap = null;
		NodeList validatorNodeList = MappingParserContext.getValidatorNodeList(sqlNode);
		if(validatorNodeList != null && validatorNodeList.getLength() > 0) {
			NamedNodeMap attributes = null;
			String name = null;
			Node attribute = null;
			for(int i=0;i<validatorNodeList.getLength();i++) {
				attributes = validatorNodeList.item(i).getAttributes();
				name = attributes.getNamedItem("name").getNodeValue();
				if(StringUtil.isEmpty(name)) 
					throw new MetadataParseException("<validator>元素中的name属性值不能为空");
					
				ValidateHandler validateHandler = new ValidateHandler(name);
				if(attributes.getLength() > 1) {
					for(int j=0;j<attributes.getLength();j++) {
						attribute = attributes.item(j);
						if(!"name".equals(attribute.getNodeName()))
							validateHandler.addValidator(ValidatorContainer.getValidatorInstance(attribute.getNodeName(), attribute.getNodeValue()));
					}
				}
				
				if(validateHandlerMap == null)
					validateHandlerMap = new HashMap<String, ValidateHandler>();
				validateHandlerMap.put(validateHandler.getName(), validateHandler);
			}
		}
		if(validateHandlerMap == null) 
			validateHandlerMap = Collections.emptyMap();
		MappingParserContext.setSqlValidateHandlers(validateHandlerMap);
	}

	/**
	 * 解析配置的 content
	 * @param sqlNode
	 * @throws XPathExpressionException 
	 */
	private void resolvingContents(Node sqlNode) throws XPathExpressionException {
		NodeList contentNodeList = MappingParserContext.getContentNodeList(sqlNode);
		if(contentNodeList == null || contentNodeList.getLength() == 0) 
			throw new MetadataParseException("至少要配置一个<content>元素");
		
		for (int i=0;i<contentNodeList.getLength();i++) 
			sqlMetadata.addContentMetadata(contentMetadataParser.parse(contentNodeList.item(i)));
	}
	
	/**
	 * 获取sql映射属性实例
	 * @param propertyNodeList
	 * @return
	 */
	private MappingProperty getSqlMappingPropertyByDocument(NodeList propertyNodeList) {
		MappingProperty property = new MappingProperty(sqlMetadata.getCode(), MappingTypeConstants.SQL);
		if(propertyNodeList != null && propertyNodeList.getLength() > 0) {
			Node propertyNode = propertyNodeList.item(0);
			if(propertyNode.hasAttributes()) {
				NamedNodeMap attributeMap = propertyNode.getAttributes();
				property.setValues(getValue(attributeMap.getNamedItem("order")), getValue(attributeMap.getNamedItem("supportCover")), getValue(attributeMap.getNamedItem("supportDelete")), getValue(attributeMap.getNamedItem("extendExpr")));
			}
		}
		return property;
	}
	private String getValue(Node attributeNode) {
		if(attributeNode == null)
			return null;
		return attributeNode.getNodeValue();
	}
}
