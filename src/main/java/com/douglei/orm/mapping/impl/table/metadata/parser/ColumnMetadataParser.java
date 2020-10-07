package com.douglei.orm.mapping.impl.table.metadata.parser;

import org.dom4j.Element;

import com.douglei.orm.dialect.datatype.util.DBDataTypeHelper;
import com.douglei.orm.dialect.datatype.util.DBDataTypeWrapper;
import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.metadata.parser.MetadataParseException;
import com.douglei.orm.mapping.metadata.parser.MetadataParser;
import com.douglei.tools.utils.StringUtil;

/**
 * 列元数据解析
 * @author DougLei
 */
public class ColumnMetadataParser implements MetadataParser<Element, ColumnMetadata>{
	
	@Override
	public ColumnMetadata parse(Element element) throws MetadataParseException{
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name)) 
			throw new MetadataParseException("<column>元素的name属性值不能为空");

		DBDataTypeWrapper wrapper = DBDataTypeHelper.get(element.attributeValue("length"), element.attributeValue("precision"), element.attributeValue("dataType"));
		
		String value = element.attributeValue("nullable");
		boolean nullable = StringUtil.isEmpty(value)?true:Boolean.parseBoolean(value);
		
		return new ColumnMetadata(
				element.attributeValue("property"), name, element.attributeValue("oldName"), 
				wrapper.getDBDataType(), wrapper.getLength(), wrapper.getPrecision(), 
				nullable,
				Boolean.parseBoolean(element.attributeValue("primaryKey")),
				Boolean.parseBoolean(element.attributeValue("unique")), element.attributeValue("defaultValue"), 
				element.attributeValue("check"), 
				element.attributeValue("fkTableName"), element.attributeValue("fkColumnName"), 
				Boolean.parseBoolean(element.attributeValue("validate")), element.attributeValue("description"));
	}
}
