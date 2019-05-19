package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dom4j.Element;

import com.douglei.configuration.LocalConfigurationDialect;
import com.douglei.database.dialect.DialectType;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataValidate;
import com.douglei.database.metadata.MetadataValidateException;
import com.douglei.database.metadata.sql.SqlContentMetadata;
import com.douglei.database.metadata.sql.content.SqlNode;
import com.douglei.database.metadata.sql.content.Type;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class XmlSqlContentMetadataValidate implements MetadataValidate {

	@Override
	public Metadata doValidate(Object obj) throws MetadataValidateException {
		return doValidate((Element)obj);
	}

	private SqlContentMetadata doValidate(Element element) {
		DialectType dialectType = getDialectType(element.attributeValue("dialect"));
		Type type = getSqlContentType(element.attributeValue("type"));
		SqlContentMetadata sqlContentMetadata =  new SqlContentMetadata(dialectType, type);
		
		// TODO
		List<SqlNode> sqlNodes = new ArrayList<SqlNode>();
		
		
		return sqlContentMetadata;
	}

	private DialectType getDialectType(String dialect) {
		DialectType type = null;
		if(StringUtil.isEmpty(dialect)) {
			type = LocalConfigurationDialect.getDialect().getType();
		}else {
			type = DialectType.toValue(dialect);
			if(type == null) {
				throw new NullPointerException("<content>元素中的dialect属性值错误:["+dialect+"], 目前支持的值包括: " + Arrays.toString(DialectType.values()));
			}
		}
		return type;
	}
	
	private Type getSqlContentType(String type) {
		Type sqlContentType = null;
		if(StringUtil.notEmpty(type)) {
			sqlContentType = Type.toValue(type);
			if(sqlContentType == null) {
				throw new NullPointerException("<content>元素中的type属性值错误:["+type+"], 目前支持的值包括: " + Arrays.toString(Type.values()));
			}
		}
		return sqlContentType;
	}
}
