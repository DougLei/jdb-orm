package com.douglei.configuration.impl.xml.element.environment.mapping.sql;

import java.util.List;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.mapping.MappingType;
import com.douglei.configuration.environment.mapping.sql.SqlMapping;
import com.douglei.configuration.impl.xml.element.environment.mapping.XmlMapping;
import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.XmlSqlContentMetadataValidate;
import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.XmlSqlMetadataValidate;
import com.douglei.configuration.impl.xml.util.ElementUtil;
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
			Element sqlElement = ElementUtil.getNecessaryAndSingleElement("<sql>", rootElement.elements("sql"));
			sqlMetadata = (SqlMetadata) sqlMetadataValidate.doValidate(sqlElement);
			List<?> contents = getContents(sqlElement);
			for (Object content : contents) {
				sqlMetadata.addContentMetadata((SqlContentMetadata)sqlContentMetadataValidate.doValidate(content));
			}
		} catch (Exception e) {
			throw new MetadataValidateException("在文件"+configFileName+"中, "+ e.getMessage());
		}
		
		logger.debug("结束解析sql类型的映射文件: {}", configFileName);
	}
	
	/**
	 * 获取<content>元素集合
	 * @param sqlElement
	 * @return
	 */
	private List<?> getContents(Element sqlElement) {
		List<?> contents = sqlElement.elements("content");
		if(contents == null || contents.size() == 0) {
			throw new MetadataValidateException("至少有一个<content>元素");
		}
		return contents;
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
