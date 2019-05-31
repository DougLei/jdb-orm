package com.douglei.configuration.impl.xml.element.environment.mapping.table.validate;

import org.dom4j.Element;

import com.douglei.core.metadata.Metadata;
import com.douglei.core.metadata.MetadataValidate;
import com.douglei.core.metadata.MetadataValidateException;
import com.douglei.core.metadata.table.ColumnMetadata;
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
		
		String value = element.attributeValue("length");
		short length = ValidationUtil.isShort(value)?Short.parseShort(value):0;
		value = element.attributeValue("precision");
		short precision = ValidationUtil.isShort(value)?Short.parseShort(value):0;
		value = element.attributeValue("nullabled");
		boolean nullabled = ValidationUtil.isBoolean(value)?Boolean.parseBoolean(value):true;
		
		return new ColumnMetadata(element.attributeValue("property"), name, element.attributeValue("dataType"), length, precision, nullabled,
				Boolean.parseBoolean(element.attributeValue("primaryKey")), Boolean.parseBoolean(element.attributeValue("unique")), element.attributeValue("defaultValue"));
	}
}
