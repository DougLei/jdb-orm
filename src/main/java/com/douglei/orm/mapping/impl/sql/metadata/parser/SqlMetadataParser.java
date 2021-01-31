package com.douglei.orm.mapping.impl.sql.metadata.parser;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.metadata.parser.MetadataParseException;
import com.douglei.orm.mapping.metadata.parser.MetadataParser;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class SqlMetadataParser implements MetadataParser<Node, SqlMetadata> {

	@Override
	public SqlMetadata parse(Node sqlNode) throws MetadataParseException {
		NamedNodeMap attributeMap = sqlNode.getAttributes();
		
		String namespace = getNamespaceValue(attributeMap.getNamedItem("namespace"));
		if(namespace == null)
			throw new MetadataParseException("<sql>元素的namespace属性值不能为空");
		
		return new SqlMetadata(namespace, getNamespaceValue(attributeMap.getNamedItem("oldNamespace")));
	}

	private String getNamespaceValue(Node namespaceItem) {
		if(namespaceItem != null) {
			String value = namespaceItem.getNodeValue();
			if(StringUtil.notEmpty(value)) 
				return value;
		}
		return null;
	}
}
