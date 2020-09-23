package com.douglei.orm.configuration.impl.element.environment.mapping.sql;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.impl.element.environment.mapping.MappingImpl;
import com.douglei.orm.configuration.impl.element.environment.mapping.MappingResolverContext;
import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.ContentMetadataResolver;
import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.ObjectMetadataResolver;
import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.SqlMetadataResolver;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataResolvingException;
import com.douglei.orm.core.metadata.sql.SqlMetadata;
import com.douglei.orm.core.metadata.validator.ValidateHandler;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class SqlMappingImpl extends MappingImpl {
	private static final Logger logger = LoggerFactory.getLogger(SqlMappingImpl.class);
	private static final SqlMetadataResolver sqlMetadataResolver = new SqlMetadataResolver();
	private static final ContentMetadataResolver contentMetadataResolver = new ContentMetadataResolver();
	private static final ObjectMetadataResolver objectMetadataResolver = new ObjectMetadataResolver();
	
	private SqlMetadata sqlMetadata;
	
	public SqlMappingImpl(String configDescription, Element rootElement) {
		super(configDescription);
		logger.debug("开始解析sql类型的映射: {}", configDescription);
		
		try {
			Node sqlNode = getSqlNode(rootElement.getElementsByTagName("sql"));
			sqlMetadata = sqlMetadataResolver.resolving(sqlNode);
			setParameterValidator(sqlNode);
			MappingResolverContext.setSqlContents(sqlNode);
			NodeList contentNodeList = getContents(sqlNode);
			for (int i=0;i<contentNodeList.getLength();i++) {
				sqlMetadata.addContentMetadata(contentMetadataResolver.resolving(contentNodeList.item(i)));
			}
		} catch (Exception e) {
			throw new MetadataResolvingException("在"+configDescription+"中, 解析出现异常", e);
		}
		logger.debug("结束解析sql类型的映射");
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
			validateHandlerMap =new HashMap<String, ValidateHandler>(validatorNodeList.getLength());
			NamedNodeMap attributes = null;
			String code = null;
			Node attribute = null;
			for(int i=0;i<validatorNodeList.getLength();i++) {
				attributes = validatorNodeList.item(i).getAttributes();
				code = attributes.getNamedItem("code").getNodeValue();
				if(StringUtil.notEmpty(code)) {
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
	 * 获取<content>元素集合
	 * @param sqlNode
	 * @return
	 * @throws Exception 
	 */
	private NodeList getContents(Node sqlNode) throws Exception {
		NodeList contentNodeList = MappingResolverContext.getContentNodeList(sqlNode);
		if(contentNodeList == null || contentNodeList.getLength() == 0) {
			throw new MetadataResolvingException("至少有一个<content>元素");
		}
		return contentNodeList;
	}

	@Override
	public MappingType getMappingType() {
		return MappingType.SQL;
	}

	@Override
	public String getCode() {
		return sqlMetadata.getCode();
	}

	@Override
	public Metadata getMetadata() {
		return sqlMetadata;
	}
}
