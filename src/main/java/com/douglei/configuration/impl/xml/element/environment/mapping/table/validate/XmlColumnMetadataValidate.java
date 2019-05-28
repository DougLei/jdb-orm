package com.douglei.configuration.impl.xml.element.environment.mapping.table.validate;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.context.DBRunEnvironmentContext;
import com.douglei.database.dialect.datatype.handler.classtype.AbstractStringDataTypeHandler;
import com.douglei.database.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataValidate;
import com.douglei.database.metadata.MetadataValidateException;
import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.database.metadata.table.column.extend.ColumnProperty;
import com.douglei.utils.StringUtil;

/**
 * 列元数据验证
 * @author DougLei
 */
public class XmlColumnMetadataValidate implements MetadataValidate{
	private static final Logger logger = LoggerFactory.getLogger(XmlColumnMetadataValidate.class);

	public Metadata doValidate(Object obj) throws MetadataValidateException{
		return doValidate((Element)obj);
	}
	
	private ColumnMetadata doValidate(Element element) {
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name)) {
			throw new MetadataValidateException("<column>元素的name属性值不能为空");
		}
		ColumnMetadata column = new ColumnMetadata(element.attributeValue("property"), getDataType(element.attributeValue("dataType")), getColumnProperty(element));
		return column;
	}

	private ClassDataTypeHandler getDataType(String dataType) throws MetadataValidateException {
		if(StringUtil.isEmpty(dataType)) {
			if(logger.isDebugEnabled()) {
				logger.debug("配置的dataType为空, 使用默认的DataTypeHandler={}", AbstractStringDataTypeHandler.class.getName());
			}
			return DBRunEnvironmentContext.getDialect().getDataTypeHandlerMapping().getDefaultClassDataTypeHandler();
		}
		try {
			return DBRunEnvironmentContext.getDialect().getDataTypeHandlerMapping().getDataTypeHandlerByCode(dataType);
		} catch (Exception e) {
			throw new MetadataValidateException("<column>元素的dataType属性值异常: " + e.getMessage());
		}
	}
	
	private ColumnProperty getColumnProperty(Element element) {
		element.attributeValue("name");
		element.attributeValue("primaryKey");
		element.attributeValue("dbType");
		element.attributeValue("length");
		element.attributeValue("precision");
		element.attributeValue("defaultValue");
		element.attributeValue("unique");
		element.attributeValue("nullabled");
		element.attributeValue("validateData");
		return null;
	}
}
