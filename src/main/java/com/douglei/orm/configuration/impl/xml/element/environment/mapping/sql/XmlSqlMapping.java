package com.douglei.orm.configuration.impl.xml.element.environment.mapping.sql;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.environment.mapping.sql.SqlMapping;
import com.douglei.orm.configuration.impl.xml.element.environment.mapping.XmlMapping;
import com.douglei.orm.configuration.impl.xml.element.environment.mapping.sql.validate.XmlContentMetadataValidate;
import com.douglei.orm.configuration.impl.xml.element.environment.mapping.sql.validate.XmlSqlMetadataValidate;
import com.douglei.orm.configuration.impl.xml.util.NotExistsElementException;
import com.douglei.orm.configuration.impl.xml.util.RepeatedElementException;
import com.douglei.orm.context.xml.MappingXmlConfigContext;
import com.douglei.orm.context.xml.MappingXmlReaderContext;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataValidateException;
import com.douglei.orm.core.metadata.sql.SqlMetadata;
import com.douglei.orm.core.metadata.validator.ValidatorHandler;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class XmlSqlMapping extends XmlMapping implements SqlMapping{
	private static final Logger logger = LoggerFactory.getLogger(XmlSqlMapping.class);
	private static final XmlSqlMetadataValidate sqlMetadataValidate = new XmlSqlMetadataValidate();
	private static final XmlContentMetadataValidate contentMetadataValidate = new XmlContentMetadataValidate();
	
	private SqlMetadata sqlMetadata;
	
	public XmlSqlMapping(String configFileName, Element rootElement) {
		super(configFileName);
		logger.debug("开始解析sql类型的映射文件: {}", configFileName);
		
		try {
			Node sqlNode = getSqlNode(rootElement.getElementsByTagName("sql"));
			sqlMetadata = sqlMetadataValidate.doValidate(sqlNode);
			setParameterValidator(sqlNode);
			MappingXmlConfigContext.initialSqlContentContainer(sqlNode);
			NodeList contentNodeList = getContents(sqlNode);
			for (int i=0;i<contentNodeList.getLength();i++) {
				sqlMetadata.addContentMetadata(contentMetadataValidate.doValidate(contentNodeList.item(i)));
			}
		} catch (Exception e) {
			throw new MetadataValidateException("在文件"+configFileName+"中, "+ e.getMessage());
		}
		logger.debug("结束解析sql类型的映射文件");
	}
	
	/**
	 * 获取唯一的<sql>元素
	 * @param sqlNodeList
	 * @return
	 */
	private Node getSqlNode(NodeList sqlNodeList) {
		if(sqlNodeList == null || sqlNodeList.getLength() == 0) {
			throw new NotExistsElementException("没有配置<sql>元素");
		}
		if(sqlNodeList.getLength() > 1) {
			throw new RepeatedElementException("<sql>元素最多只能配置一个");
		}
		return sqlNodeList.item(0);
	}
	
	/**
	 * 设置配置的参数验证器
	 * @param sqlNode
	 */
	private void setParameterValidator(Node sqlNode) {
		Map<String, ValidatorHandler> validatorHandlerMap = null;
		NodeList validatorNodeList = MappingXmlReaderContext.getValidatorNodeList(sqlNode);
		if(validatorNodeList != null && validatorNodeList.getLength() > 0) {
			validatorHandlerMap =new HashMap<String, ValidatorHandler>(validatorNodeList.getLength());
			NamedNodeMap attributes = null;
			String name = null;
			Node attribute = null;
			for(int i=0;i<validatorNodeList.getLength();i++) {
				attributes = validatorNodeList.item(i).getAttributes();
				name = attributes.getNamedItem("name").getNodeValue();
				if(StringUtil.notEmpty(name)) {
					ValidatorHandler handler = new ValidatorHandler(name, true);
					if(attributes.getLength() > 1) {
						for(int j=0;j<attributes.getLength();j++) {
							attribute = attributes.item(j);
							if(!"name".equals(attribute.getNodeName())) {
								handler.addValidator(attribute.getNodeName(), attribute.getNodeValue());
							}
						}
					}
					validatorHandlerMap.put(handler.getName(), handler);
					continue;
				}
				throw new NullPointerException("<validator>元素中的name属性值不能为空");
			}
		}
		if(validatorHandlerMap == null) {
			validatorHandlerMap = Collections.emptyMap();
		}
		MappingXmlConfigContext.setSqlValidatorMap(validatorHandlerMap);
	}

	/**
	 * 获取<content>元素集合
	 * @param sqlNode
	 * @return
	 */
	private NodeList getContents(Node sqlNode) {
		NodeList contentNodeList = MappingXmlReaderContext.getContentNodeList(sqlNode);
		if(contentNodeList == null || contentNodeList.getLength() == 0) {
			throw new MetadataValidateException("至少有一个<content>元素");
		}
		return contentNodeList;
	}

	@Override
	public MappingType getMappingType() {
		return MappingType.SQL;
	}

	public String getCode() {
		return sqlMetadata.getCode();
	}

	@Override
	public Metadata getMetadata() {
		return sqlMetadata;
	}
}
