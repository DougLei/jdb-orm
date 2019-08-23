package com.douglei.orm.configuration.impl.xml.element.environment.mapping.sql.validate;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.douglei.orm.core.metadata.MetadataValidate;
import com.douglei.orm.core.metadata.MetadataValidateException;
import com.douglei.orm.core.metadata.sql.SqlMetadata;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class XmlSqlMetadataValidate implements MetadataValidate<Node, SqlMetadata> {

	@Override
	public SqlMetadata doValidate(Node sqlNode) throws MetadataValidateException {
		NamedNodeMap attributeMap = sqlNode.getAttributes();
		return new SqlMetadata(getNamespace(attributeMap.getNamedItem("namespace")));
	}
	
	private String getNamespace(Node namespaceItem) {
		if(namespaceItem == null) {
			throw new MetadataValidateException("<sql>元素的namespace属性值不能为空");
		}
		String namespace = namespaceItem.getNodeValue();
		if(StringUtil.isEmpty(namespace)) {
			throw new MetadataValidateException("<sql>元素的namespace属性值不能为空");
		}
		return namespace;
	}
}
