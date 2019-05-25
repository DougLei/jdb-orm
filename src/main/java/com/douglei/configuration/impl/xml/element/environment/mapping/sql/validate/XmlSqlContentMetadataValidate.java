package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate;

import java.util.Arrays;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.configuration.LocalConfigurationDialectHolder;
import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.SqlNodeHandlerMapping;
import com.douglei.database.dialect.DialectType;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataValidate;
import com.douglei.database.metadata.MetadataValidateException;
import com.douglei.database.metadata.sql.SqlContentMetadata;
import com.douglei.database.metadata.sql.SqlContentType;
import com.douglei.database.metadata.sql.content.node.SqlNode;

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
		
		DialectType dialectType = getDialectType(attributeMap.getNamedItem("dialect"));
		SqlContentType type = getSqlContentType(attributeMap.getNamedItem("type"));
		SqlContentMetadata sqlContentMetadata = new SqlContentMetadata(dialectType, type);
		
		NodeList children = contentNode.getChildNodes();
		int length = children.getLength();
		SqlNode sqlNode = null;
		for(int i=0;i<length;i++) {
			sqlNode = SqlNodeHandlerMapping.doHandler(children.item(i));
			if(sqlNode != null) {
				sqlContentMetadata.addRootSqlNode(sqlNode);
			}
		}
		return sqlContentMetadata;
	}

	private DialectType getDialectType(Node dialect) {
		DialectType type = null;
		if(dialect == null) {
			type = LocalConfigurationDialectHolder.getDialect().getType();
		}else {
			type = DialectType.toValue(dialect.getNodeValue());
			if(type == null) {
				throw new NullPointerException("<content>元素中的dialect属性值错误:["+dialect+"], 目前支持的值包括: " + Arrays.toString(DialectType.values()));
			}
		}
		return type;
	}
	
	private SqlContentType getSqlContentType(Node type) {
		if(type == null) {
			throw new MetadataValidateException("<content>元素的type属性值不能为空");
		}else {
			SqlContentType sqlContentType = SqlContentType.toValue(type.getNodeValue());
			if(sqlContentType == null) {
				throw new NullPointerException("<content>元素中的type属性值错误:["+type+"], 目前支持的值包括: " + Arrays.toString(SqlContentType.values()));
			}
			return sqlContentType;
		}
	}
}
