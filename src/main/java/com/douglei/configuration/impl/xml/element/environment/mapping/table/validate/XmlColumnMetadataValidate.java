package com.douglei.configuration.impl.xml.element.environment.mapping.table.validate;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.context.DBContext;
import com.douglei.database.dialect.datatype.DataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.impl.AbstractStringDataTypeHandler;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataValidate;
import com.douglei.database.metadata.MetadataValidateException;
import com.douglei.database.metadata.table.ColumnMetadata;
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
		DataTypeHandler dataTypeHandler = validateDataType(element.attributeValue("dataType"));
		return new ColumnMetadata(name, dataTypeHandler, element.attributeValue("property"), validateIsPrimaryKey(element.attributeValue("primaryKey")));
	}

	private DataTypeHandler validateDataType(String dataType) throws MetadataValidateException {
		if(StringUtil.isEmpty(dataType)) {
			if(logger.isDebugEnabled()) {
				logger.debug("配置的dataType为空, 使用默认的DataTypeHandler={}", AbstractStringDataTypeHandler.class.getName());
			}
			return DBContext.getDialect().getDataTypeHandlerMapping().getDefaultDataTypeHandler();
		}
		try {
			return DBContext.getDialect().getDataTypeHandlerMapping().getDataTypeHandlerByCode(dataType);
		} catch (Exception e) {
			throw new MetadataValidateException("<column>元素的dataType属性值异常: " + e.getMessage());
		}
	}
	
	private boolean validateIsPrimaryKey(String primaryKey) {
		return Boolean.parseBoolean(primaryKey);
	}
}
