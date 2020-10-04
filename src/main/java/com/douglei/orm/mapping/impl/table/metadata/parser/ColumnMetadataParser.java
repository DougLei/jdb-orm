package com.douglei.orm.mapping.impl.table.metadata.parser;

import org.dom4j.Element;

import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.metadata.parser.MetadataParseException;
import com.douglei.orm.mapping.metadata.parser.MetadataParser;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 列元数据解析
 * @author DougLei
 */
public class ColumnMetadataParser implements MetadataParser<Element, ColumnMetadata>{
	
	@Override
	public ColumnMetadata parse(Element element) throws MetadataParseException{
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name)) {
			throw new MetadataParseException("<column>元素的name属性值不能为空");
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
