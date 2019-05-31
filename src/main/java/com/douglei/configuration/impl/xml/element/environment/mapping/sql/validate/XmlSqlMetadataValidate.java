package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.douglei.core.metadata.Metadata;
import com.douglei.core.metadata.MetadataValidate;
import com.douglei.core.metadata.MetadataValidateException;
import com.douglei.core.metadata.sql.SqlMetadata;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class XmlSqlMetadataValidate implements MetadataValidate {

	@Override
	public Metadata doValidate(Object obj) throws MetadataValidateException {
		return doValidate((Node)obj);
	}
	
	private SqlMetadata doValidate(Node sqlNode) {
		NamedNodeMap attributeMap = sqlNode.getAttributes();
		String name = getName(attributeMap.getNamedItem("name"));
		String namespace = getNamespace(attributeMap.getNamedItem("namespace"));
		return new SqlMetadata(namespace, name);
	}

	private String getName(Node nameItem) {
		if(nameItem == null) {
			throw new MetadataValidateException("<sql>元素的name属性值不能为空");
		}
		String name = nameItem.getNodeValue();
		if(StringUtil.isEmpty(name)) {
			throw new MetadataValidateException("<sql>元素的name属性值不能为空");
		}
		return name;
	}
	
	private String getNamespace(Node namespaceItem) {
		if(namespaceItem == null) {
			return null;
		}
		String namespace = namespaceItem.getNodeValue();
		if(StringUtil.isEmpty(namespace)) {
			return null;
		}
		return namespace;
	}
}
