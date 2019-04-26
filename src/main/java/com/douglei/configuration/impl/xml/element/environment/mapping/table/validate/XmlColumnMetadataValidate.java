package com.douglei.configuration.impl.xml.element.environment.mapping.table.validate;

import org.dom4j.Element;

import com.douglei.database.datatype.DataType;
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

	public Metadata doValidate(Object obj) throws MetadataValidateException{
		return doValidate((Element)obj);
	}
	
	private ColumnMetadata doValidate(Element element) {
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name)) {
			throw new MetadataValidateException("<column>元素的name属性值不能为空");
		}
		DataType dataType = validateDataType(element.attributeValue("dataType"));
		return new ColumnMetadata(name, dataType, element.attributeValue("propertyName"));
	}

	private DataType validateDataType(String dataType) throws MetadataValidateException {
		if(StringUtil.isEmpty(dataType)) {
			throw new MetadataValidateException("<column>元素的dataType属性值不能为空");
		}
		try {
			return DataType.toValue(dataType);
		} catch (Exception e) {
			throw new MetadataValidateException("<column>元素的dataType属性值: " + e.getMessage());
		}
	}
}
