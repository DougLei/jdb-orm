package com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.douglei.orm.core.metadata.MetadataResolver;
import com.douglei.orm.core.metadata.MetadataValidateException;
import com.douglei.orm.core.metadata.sql.SqlMetadata;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class SqlMetadataResolver implements MetadataResolver<Node, SqlMetadata> {

	@Override
	public SqlMetadata resolving(Node sqlNode) throws MetadataValidateException {
		NamedNodeMap attributeMap = sqlNode.getAttributes();
		return new SqlMetadata(getNamespace(attributeMap.getNamedItem("namespace")));
	}
	
	private String getNamespace(Node namespaceItem) {
		if(namespaceItem != null) {
			String namespace = namespaceItem.getNodeValue();
			if(StringUtil.notEmpty(namespace)) {
				return namespace;
			}
		}
		throw new MetadataValidateException("<sql>元素的namespace属性值不能为空");
	}
}
