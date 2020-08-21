package com.douglei.orm.configuration.impl.xml.element.environment.mapping.sql.validate;

import java.util.Arrays;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.context.xml.MappingXmlConfigContext;
import com.douglei.orm.core.metadata.MetadataValidateException;
import com.douglei.orm.core.metadata.sql.ContentType;

/**
 * <content>
 * @author DougLei
 */
public class XmlContentMetadataValidator extends XmlSqlContentMetadataValidator {
	private static final String nodeName = "<content>";
	
	@Override
	protected String getNodeName() {
		return nodeName;
	}

	@Override
	protected ContentType getContentType(NamedNodeMap attributeMap) {
		Node type = attributeMap.getNamedItem("type");
		if(type  == null) {
			throw new MetadataValidateException("<content>元素的type属性值不能为空");
		}else {
			ContentType sqlContentType = ContentType.toValue(type.getNodeValue());
			if(sqlContentType == null) {
				throw new MetadataValidateException("<content>元素中的type属性值错误:["+type+"], 目前支持的值包括: " + Arrays.toString(ContentType.values()));
			}
			MappingXmlConfigContext.setContentType(sqlContentType);
			return sqlContentType;
		}
	}

	@Override
	protected void doValidateProcedureContent(int childrenLength, NodeList children) {
		if(MappingXmlConfigContext.getContentType() == ContentType.PROCEDURE) {
			short nodeType, textNodeCount = 0, otherNodeCount = 0;
			for(int i=0;i<childrenLength;i++) {
				nodeType = children.item(i).getNodeType();
				if(nodeType != Node.COMMENT_NODE) {
					if(nodeType == Node.TEXT_NODE) {
						textNodeCount++;
					}else {
						otherNodeCount++;
					}
				}
			}
			if(textNodeCount == 0 || otherNodeCount > 0) {
				throw new MetadataValidateException("<content type='procedure'>时, 其中必须配置, 且只能配置sql文本内容 {call procedure_name([parameter...])}, 不能配置其他元素内容");
			}
		}
	}
}
