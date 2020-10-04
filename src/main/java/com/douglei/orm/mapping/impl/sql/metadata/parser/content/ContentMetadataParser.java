package com.douglei.orm.mapping.impl.sql.metadata.parser.content;

import java.util.Arrays;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.mapping.context.parser.MappingParserContext;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.metadata.parser.MetadataParseException;

/**
 * <content>
 * @author DougLei
 */
public class ContentMetadataParser extends SqlContentMetadataParser {
	
	@Override
	protected String getNodeName() {
		return "<content>";
	}

	@Override
	protected boolean isSqlContentNode() {
		return false;
	}

	@Override
	protected ContentType getContentType(NamedNodeMap attributeMap) {
		Node type = attributeMap.getNamedItem("type");
		if(type  == null) {
			throw new MetadataParseException("<content>元素的type属性值不能为空");
		}else {
			ContentType sqlContentType = ContentType.toValue(type.getNodeValue());
			if(sqlContentType == null) {
				throw new MetadataParseException("<content>元素中的type属性值错误:["+type+"], 目前支持的值包括: " + Arrays.toString(ContentType.values()));
			}
			MappingParserContext.setCurrentSqlType(sqlContentType);
			return sqlContentType;
		}
	}

	@Override
	protected void validateProcedureContent(int childrenLength, NodeList children) {
		if(MappingParserContext.getCurrentSqlType() == ContentType.PROCEDURE) {
			short nodeType, textNodeCount = 0, otherNodeCount = 0;
			for(int i=0;i<childrenLength;i++) {
				nodeType = children.item(i).getNodeType();
				if(nodeType == Node.COMMENT_NODE)
					continue;
				if(nodeType == Node.TEXT_NODE) {
					textNodeCount++;
					continue;
				}
				otherNodeCount=1;
				break;
			}
			if(textNodeCount == 0 || otherNodeCount > 0) 
				throw new MetadataParseException("<content type='procedure'>时, 其中必须, 且只能配置sql文本内容 {call procedure_name([parameter...])}或注释, 不能配置其他元素内容");
		}
	}
}
