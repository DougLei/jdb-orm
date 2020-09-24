package com.douglei.orm.configuration.impl.element.environment.mapping.sql;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.impl.element.environment.mapping.MappingResolverContext;
import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.SqlMetadataResolver;
import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.ContentMetadataResolver;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataResolvingException;
import com.douglei.orm.core.metadata.sql.SqlMetadata;
import com.douglei.orm.core.metadata.validator.ValidateHandler;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class SqlMappingImpl implements Mapping {
	private static final SqlMetadataResolver sqlMetadataResolver = new SqlMetadataResolver();
	private static final ContentMetadataResolver contentMetadataResolver = new ContentMetadataResolver();
	
	private SqlMetadata sqlMetadata;
	
	public SqlMappingImpl(Element rootElement) throws XPathExpressionException {
		Node sqlNode = getSqlNode(rootElement.getElementsByTagName(getMappingType().getName()));
		sqlMetadata = sqlMetadataResolver.resolving(sqlNode);
		
		setParameterValidator(sqlNode);
		MappingResolverContext.setSqlContents(sqlNode);
		
		resolvingContents(sqlNode);
	}
	
	/**
	 * 获取唯一的<sql>元素
	 * @param sqlNodeList
	 * @return
	 */
	private Node getSqlNode(NodeList sqlNodeList) {
		if(sqlNodeList == null || sqlNodeList.getLength() == 0) 
			throw new MetadataResolvingException("没有配置<sql>元素");
		if(sqlNodeList.getLength() > 1) 
			throw new MetadataResolvingException("<sql>元素最多只能配置一个");
		return sqlNodeList.item(0);
	}
	
	/**
	 * 设置配置的参数验证器
	 * @param sqlNode
	 * @throws XPathExpressionException 
	 */
	private void setParameterValidator(Node sqlNode) throws XPathExpressionException {
		Map<String, ValidateHandler> validateHandlerMap = null;
		NodeList validatorNodeList = MappingResolverContext.getValidatorNodeList(sqlNode);
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
				throw new MetadataResolvingException("<validator>元素中的code属性值不能为空");
			}
		}
		if(validateHandlerMap == null) 
			validateHandlerMap = Collections.emptyMap();
		MappingResolverContext.setSqlValidateHandlers(validateHandlerMap);
	}

	/**
	 * 解析配置的 content
	 * @param sqlNode
	 * @throws XPathExpressionException 
	 */
	private void resolvingContents(Node sqlNode) throws XPathExpressionException {
		NodeList contentNodeList = MappingResolverContext.getContentNodeList(sqlNode);
		if(contentNodeList == null || contentNodeList.getLength() == 0) 
			throw new MetadataResolvingException("至少要配置一个<content>元素");
		
		for (int i=0;i<contentNodeList.getLength();i++) 
			sqlMetadata.addContentMetadata(contentMetadataResolver.resolving(contentNodeList.item(i)));
	}
	
	@Override
	public String getCode() {
		return sqlMetadata.getCode();
	}
	
	@Override
	public MappingType getMappingType() {
		return MappingType.SQL;
	}

	@Override
	public Metadata getMetadata() {
		return sqlMetadata;
	}
}
