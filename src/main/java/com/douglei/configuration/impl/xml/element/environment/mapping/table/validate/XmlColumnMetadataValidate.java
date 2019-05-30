package com.douglei.configuration.impl.xml.element.environment.mapping.table.validate;

import org.dom4j.Element;

import com.douglei.context.DBRunEnvironmentContext;
import com.douglei.database.dialect.datatype.DataType;
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
		DBRunEnvironmentContext.getDialect().getDBObjectNameHandler().validateDBObjectName(name);
		
		String value = element.attributeValue("length");
		short length = ValidationUtil.isShort(value)?Short.parseShort(value):0;
		value = element.attributeValue("precision");
		short precision = ValidationUtil.isShort(value)?Short.parseShort(value):0;
		value = element.attributeValue("nullabled");
		boolean nullabled = ValidationUtil.isBoolean(value)?Boolean.parseBoolean(value):true;
		
		return new ColumnMetadata(element.attributeValue("property"), name, DataType.toValue(element.attributeValue("dataType")), length, precision, nullabled,
				Boolean.parseBoolean(element.attributeValue("primaryKey")), Boolean.parseBoolean(element.attributeValue("unique")), element.attributeValue("defaultValue"));
	}
}
