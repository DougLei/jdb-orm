package com.douglei.orm.mapping.impl.sql.parser.content;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.douglei.orm.mapping.MappingParseToolContext;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.metadata.MetadataParseException;

/**
 * <content>
 * @author DougLei
 */
public class ContentMetadataParser extends SqlContentMetadataParser {
	
	@Override
	protected ContentType parseContentType(NamedNodeMap attributeMap) {
		Node type = attributeMap.getNamedItem("type");
		if(type  == null) 
			throw new MetadataParseException("<content>中的type属性值不能为空");
		
		ContentType sqlContentType = ContentType.valueOf(type.getNodeValue().toUpperCase());
		MappingParseToolContext.getMappingParseTool().setCurrentSqlContentType(sqlContentType);
		return sqlContentType;
	}
}
