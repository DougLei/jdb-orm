package com.douglei.orm.mapping.impl.table.metadata.parser;

import org.dom4j.Element;

import com.douglei.orm.EnvironmentContext;
import com.douglei.orm.dialect.datatype.Classification;
import com.douglei.orm.dialect.datatype.DataType;
import com.douglei.orm.dialect.datatype.mapping.MappingDataType;
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
		if(StringUtil.isEmpty(name)) 
			throw new MetadataParseException("<column>元素的name属性值不能为空");
		
		MappingDataType dataType = getDataType(element.attributeValue("dataType"));
		
		String value = element.attributeValue("length");
		int length = VerifyTypeMatchUtil.isLimitInteger(value)?Integer.parseInt(value):0;
		
		value = element.attributeValue("precision");
		int precision = VerifyTypeMatchUtil.isLimitInteger(value)?Integer.parseInt(value):0;
		
		value = element.attributeValue("nullable");
		boolean nullable = value == null?true:Boolean.parseBoolean(value);
		
		return new ColumnMetadata(
				element.attributeValue("property"), name, element.attributeValue("oldName"), element.attributeValue("description"), 
				dataType, length, precision, 
				nullable,
				Boolean.parseBoolean(element.attributeValue("primaryKey")),
				Boolean.parseBoolean(element.attributeValue("unique")), element.attributeValue("defaultValue"), 
				element.attributeValue("check"), 
				element.attributeValue("fkTableName"), element.attributeValue("fkColumnName"), 
				Boolean.parseBoolean(element.attributeValue("validate")));
	}
	
	/**
	 * 获取数据类型
	 * @param value
	 * @return
	 */
	private MappingDataType getDataType(String value) {
		if(StringUtil.isEmpty(value))
			value = MappingDataType.DEFAULT_NAME; // 数据类型默认是字符串

		DataType dataType = EnvironmentContext.getDialect().getDataTypeContainer().get(value);
		if(dataType.getClassification() != Classification.MAPPING)
			throw new MetadataParseException("列的数据类型, 必须配置为"+Classification.MAPPING+"分类下的数据类型");
		return (MappingDataType) dataType;
	}
}
