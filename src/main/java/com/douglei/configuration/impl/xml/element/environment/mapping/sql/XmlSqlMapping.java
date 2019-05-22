package com.douglei.configuration.impl.xml.element.environment.mapping.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.configuration.environment.mapping.MappingType;
import com.douglei.configuration.environment.mapping.sql.SqlMapping;
import com.douglei.configuration.impl.xml.LocalXmlConfigurationXMLReaderHolder;
import com.douglei.configuration.impl.xml.element.environment.mapping.XmlMapping;
import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.XmlSqlContentMetadataValidate;
import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.XmlSqlMetadataValidate;
import com.douglei.configuration.impl.xml.util.NotExistsElementException;
import com.douglei.configuration.impl.xml.util.RepeatElementException;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataValidate;
import com.douglei.database.metadata.MetadataValidateException;
import com.douglei.database.metadata.sql.SqlContentMetadata;
import com.douglei.database.metadata.sql.SqlMetadata;

/**
 * 
 * @author DougLei
 */
public class XmlSqlMapping extends XmlMapping implements SqlMapping{
	private static final Logger logger = LoggerFactory.getLogger(XmlSqlMapping.class);
	
	private static final MetadataValidate sqlMetadataValidate = new XmlSqlMetadataValidate();
	private static final MetadataValidate sqlContentMetadataValidate = new XmlSqlContentMetadataValidate();
	
	private SqlMetadata sqlMetadata;
	
	public XmlSqlMapping(String configFileName, Element rootElement) {
		super(configFileName);
		logger.debug("开始解析sql类型的映射文件: {}", configFileName);
		
		try {
			Node sqlNode = getSqlNode(rootElement.getElementsByTagName("sql"));
			sqlMetadata = (SqlMetadata) sqlMetadataValidate.doValidate(sqlNode);
			NodeList contentNodeList = getContents(sqlNode);
			int length = contentNodeList.getLength();
			for (int i=0;i<length ;i++) {
				sqlMetadata.addContentMetadata((SqlContentMetadata)sqlContentMetadataValidate.doValidate(contentNodeList.item(i)));
			}
		} catch (Exception e) {
			throw new MetadataValidateException("在文件"+configFileName+"中, "+ e.getMessage());
		}
		
		logger.debug("结束解析sql类型的映射文件: {}", configFileName);
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
			throw new RepeatElementException("<sql>元素最多只能配置一个");
		}
		return sqlNodeList.item(0);
	}

	/**
	 * 获取<content>元素集合
	 * @param sqlNode
	 * @return
	 */
	private NodeList getContents(Node sqlNode) {
		NodeList contentNodeList = LocalXmlConfigurationXMLReaderHolder.getContentNodeList(sqlNode);
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
