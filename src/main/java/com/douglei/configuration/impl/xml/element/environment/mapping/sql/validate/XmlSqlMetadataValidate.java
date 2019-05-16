package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate;

import org.dom4j.Element;

import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataValidate;
import com.douglei.database.metadata.MetadataValidateException;
import com.douglei.database.metadata.sql.SqlMetadata;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class XmlSqlMetadataValidate implements MetadataValidate {

	@Override
	public Metadata doValidate(Object obj) throws MetadataValidateException {
		return doValidate((Element)obj);
	}
	
	private SqlMetadata doValidate(Element element) {
		String name = element.attributeValue("name");
		if(StringUtil.isEmpty(name)) {
			throw new MetadataValidateException("<sql>元素的name属性值不能为空");
		}
		return new SqlMetadata(element.attributeValue("namespace"), name);
	}
}
