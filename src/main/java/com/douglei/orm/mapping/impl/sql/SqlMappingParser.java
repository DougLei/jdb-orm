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

import com.douglei.orm.mapping.impl.MappingParserContext;
import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.parser.SqlMetadataParser;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.ContentMetadataParser;
import com.douglei.orm.mapping.metadata.parser.MetadataParseException;
import com.douglei.orm.mapping.metadata.validator.ValidateHandler;
import com.douglei.orm.mapping.type.MappingTypeNameConstants;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
class SqlMappingParser {
	private static final SqlMetadataParser sqlMetadataParser = new SqlMetadataParser();
	private static final ContentMetadataParser contentMetadataParser = new ContentMetadataParser();
	
	private SqlMetadata sqlMetadata;
	
	/**
	 * 解析
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public SqlMapping parse(InputStream input) throws Exception {
		Document sqlDocument = MappingParserContext.getDocumentBuilder().parse(input);
		Element rootElement = sqlDocument.getDocumentElement();
		
		Node sqlNode = getSqlNode(rootElement.getElementsByTagName(MappingTypeNameConstants.SQL));
		this.sqlMetadata = sqlMetadataParser.parse(sqlNode);
		
		setParameterValidator(sqlNode);
		MappingParserContext.setSqlContents(sqlNode);
		
		resolvingContents(sqlNode);
		
		return new SqlMapping(sqlMetadata);
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
			String code = null;
			Node attribute = null;
			for(int i=0;i<validatorNodeList.getLength();i++) {
				attributes = validatorNodeList.item(i).getAttributes();
				
				code = attributes.getNamedItem("code").getNodeValue();
				if(StringUtil.notEmpty(code)) {
					if(validateHandlerMap == null)
						validateHandlerMap = new HashMap<String, ValidateHandler>(validatorNodeList.getLength());
					
					ValidateHandler handler = new ValidateHandler(code, true);
					if(attributes.getLength() > 1) {
						for(int j=0;j<attributes.getLength();j++) {
							attribute = attributes.item(j);
							if(!"code".equals(attribute.getNodeName()))
								handler.addValidator(attribute.getNodeName(), attribute.getNodeValue());
						}
					}
					validateHandlerMap.put(handler.getCode(), handler);
					continue;
				}
				throw new MetadataParseException("<validator>元素中的code属性值不能为空");
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
	
}