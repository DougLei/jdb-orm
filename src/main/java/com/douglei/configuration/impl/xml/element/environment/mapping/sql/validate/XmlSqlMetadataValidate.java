package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataValidate;
import com.douglei.database.metadata.MetadataValidateException;
import com.douglei.database.metadata.sql.SqlMetadata;

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
		Node name = attributeMap.getNamedItem("name");
		if(name == null) {
			throw new MetadataValidateException("<sql>元素的name属性值不能为空");
		}
		return new SqlMetadata(attributeMap.getNamedItem("namespace").getNodeValue(), name.getNodeValue());
	}
}
