package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate;

import java.util.Arrays;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.douglei.configuration.LocalConfigurationDialect;
import com.douglei.database.dialect.DialectType;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataValidate;
import com.douglei.database.metadata.MetadataValidateException;
import com.douglei.database.metadata.sql.SqlContentMetadata;
import com.douglei.database.metadata.sql.content.Type;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class XmlSqlContentMetadataValidate implements MetadataValidate {

	@Override
	public Metadata doValidate(Object obj) throws MetadataValidateException {
		return doValidate((Node)obj);
	}

	private SqlContentMetadata doValidate(Node contentNode) {
		NamedNodeMap attributeMap = contentNode.getAttributes();
		
		DialectType dialectType = getDialectType(attributeMap.getNamedItem("dialect").getNodeValue());
		Type type = getSqlContentType(attributeMap.getNamedItem("type").getNodeValue());
		SqlContentMetadata sqlContentMetadata =  new SqlContentMetadata(dialectType, type);
		
		
		
		
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
