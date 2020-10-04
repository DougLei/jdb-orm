package com.douglei.orm.mapping.impl.sql.metadata.parser;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.metadata.parser.MetadataParseException;
import com.douglei.orm.mapping.metadata.parser.MetadataParser;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class SqlMetadataParser implements MetadataParser<Node, SqlMetadata> {

	@Override
	public SqlMetadata parse(Node sqlNode) throws MetadataParseException {
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
		throw new MetadataParseException("<sql>元素的namespace属性值不能为空");
	}
}
