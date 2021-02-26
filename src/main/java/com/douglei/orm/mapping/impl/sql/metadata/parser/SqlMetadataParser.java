package com.douglei.orm.mapping.impl.sql.metadata.parser;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.metadata.SqlMetadata;
import com.douglei.orm.mapping.metadata.MetadataParseException;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class SqlMetadataParser {

	/**
	 * 解析SqlMetadata
	 * @param sqlNode
	 * @return
	 * @throws MetadataParseException
	 */
	public SqlMetadata parse(Node sqlNode) throws MetadataParseException {
		NamedNodeMap attributeMap = sqlNode.getAttributes();
		
		// 解析name
		String namespace = getNamespaceValue(attributeMap.getNamedItem("namespace"));
		if(namespace == null)
			throw new MetadataParseException("<sql>的namespace属性值不能为空");
		
		// 解析oldName
		String oldNamespace = getNamespaceValue(attributeMap.getNamedItem("oldNamespace"));
		
		return new SqlMetadata(namespace, oldNamespace);
	}
	
	// 获取属性值
	private String getNamespaceValue(Node node) {
		if(node != null) {
			String value = node.getNodeValue();
			if(StringUtil.unEmpty(value)) 
				return value;
		}
		return null;
	}
}
