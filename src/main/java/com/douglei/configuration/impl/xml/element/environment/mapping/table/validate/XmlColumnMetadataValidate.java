package com.douglei.configuration.impl.xml.element.environment.mapping.table.validate;

import org.dom4j.Element;

import com.douglei.database.datatype.DataTypeHandler;
import com.douglei.database.datatype.DataTypeHandlerMapping;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataValidate;
import com.douglei.database.metadata.MetadataValidateException;
import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.utils.StringUtil;
import com.douglei.utils.datatype.ValidationUtil;

/**
 * 列元数据验证
 * @author DougLei
 */
public class XmlColumnMetadataValidate implements MetadataValidate{

	public Metadata doValidate(Object obj) throws MetadataValidateException{
		return doValidate((Element)obj);
	}
	
	private ColumnMetadata doValidate(Element element) {
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name)) {
			throw new MetadataValidateException("<column>元素的name属性值不能为空");
		}
		DataTypeHandler dataTypeHandler = validateDataType(element.attributeValue("dataType"));
		boolean lazyload = validateLazyload(element.attributeValue("lazyload"), dataTypeHandler);
		return new ColumnMetadata(name, dataTypeHandler, element.attributeValue("propertyName"), lazyload);
	}

	private DataTypeHandler validateDataType(String dataType) throws MetadataValidateException {
		if(StringUtil.isEmpty(dataType)) {
			throw new MetadataValidateException("<column>元素的dataType属性值不能为空");
		}
		try {
			return DataTypeHandlerMapping.getDataTypeHandler(dataType);
		} catch (Exception e) {
			throw new MetadataValidateException("<column>元素的dataType属性值: ", e);
		}
	}
	
	private boolean validateLazyload(String lazyload, DataTypeHandler dataTypeHandler) {
		if(ValidationUtil.isBoolean(lazyload)) {
			return Boolean.parseBoolean(lazyload);
		}
		if(dataTypeHandler.isIOStream()) {
			return true;
		}
		return false;
	}
}
