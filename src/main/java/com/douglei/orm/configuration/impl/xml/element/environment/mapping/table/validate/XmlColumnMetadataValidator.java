package com.douglei.orm.configuration.impl.xml.element.environment.mapping.table.validate;

import org.dom4j.Element;

import com.douglei.orm.core.metadata.MetadataValidator;
import com.douglei.orm.core.metadata.MetadataValidateException;
import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 列元数据验证
 * @author DougLei
 */
public class XmlColumnMetadataValidator implements MetadataValidator<Element, ColumnMetadata>{
	
	public ColumnMetadata doValidate(Element element) throws MetadataValidateException{
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name)) {
			throw new MetadataValidateException("<column>元素的name属性值不能为空");
		}
		
		String value = element.attributeValue("length");
		short length = VerifyTypeMatchUtil.isLimitShort(value)?Short.parseShort(value):0;
		
		value = element.attributeValue("precision");
		short precision = VerifyTypeMatchUtil.isLimitShort(value)?Short.parseShort(value):0;
		
		value = element.attributeValue("nullable");
		boolean nullable = value == null?true:Boolean.parseBoolean(value);
		
		return new ColumnMetadata(
				element.attributeValue("property"), name, element.attributeValue("oldName"), element.attributeValue("descriptionName"), 
				element.attributeValue("dataType"), length, precision, 
				nullable,
				Boolean.parseBoolean(element.attributeValue("primaryKey")),
				Boolean.parseBoolean(element.attributeValue("unique")), element.attributeValue("defaultValue"), 
				element.attributeValue("check"), 
				element.attributeValue("fkTableName"), element.attributeValue("fkColumnName"), 
				Boolean.parseBoolean(element.attributeValue("validate")));
	}
}
