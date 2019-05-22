package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate;

import java.util.Arrays;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.configuration.LocalConfigurationDialectHolder;
import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.NodeMetadataValidateMapping;
import com.douglei.database.dialect.DialectType;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataValidate;
import com.douglei.database.metadata.MetadataValidateException;
import com.douglei.database.metadata.sql.SqlContentMetadata;
import com.douglei.database.metadata.sql.Type;
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
		SqlContentMetadata sqlContentMetadata = new SqlContentMetadata(dialectType, type);
		
		NodeList children = contentNode.getChildNodes();
		int length = children.getLength();
		Node node = null;
		for(int i=0;i<length;i++) {
			node = children.item(i);
			if(node.getNodeType() == Node.COMMENT_NODE) {
				continue;
			}
			sqlContentMetadata.addNodeMetadata(NodeMetadataValidateMapping.doValidate(node));
		}
		return sqlContentMetadata;
	}

	private DialectType getDialectType(String dialect) {
		DialectType type = null;
		if(StringUtil.isEmpty(dialect)) {
			type = LocalConfigurationDialectHolder.getDialect().getType();
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
