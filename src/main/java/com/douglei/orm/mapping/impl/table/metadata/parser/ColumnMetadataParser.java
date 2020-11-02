package com.douglei.orm.mapping.impl.table.metadata.parser;

import org.dom4j.Element;

import com.douglei.orm.dialect.datatype.db.util.DBDataTypeUtil;
import com.douglei.orm.dialect.datatype.db.util.DBDataTypeWrapper;
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

		String type = element.attributeValue("dbType");
		if(StringUtil.isEmpty(type))
			type = element.attributeValue("dataType");
		if(StringUtil.isEmpty(type))
			type = "string";
		// 这里不对type进行转大小写的操作, 原因是, dataType可能是一个自定义类的全路径, 转大小写后无法进行反射构建实例
		DBDataTypeWrapper wrapper = DBDataTypeUtil.get(element.attributeValue("length"), element.attributeValue("precision"), type);
		
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
