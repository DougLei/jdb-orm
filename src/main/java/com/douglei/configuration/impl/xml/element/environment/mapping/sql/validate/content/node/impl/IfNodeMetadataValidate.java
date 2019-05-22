package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.impl;

import org.w3c.dom.Node;

import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.NodeMetadataValidate;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataValidateException;
import com.douglei.database.metadata.sql.content.node.impl.IfNodeMetadata;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class IfNodeMetadataValidate implements NodeMetadataValidate {

	@Override
	public Metadata doValidate(Object obj) throws MetadataValidateException {
		return doValidate((Node)obj);
	}
	
	private Metadata doValidate(Node node) {
		String content = node.getTextContent();
		if(StringUtil.isEmpty(content)) {
			throw new MetadataValidateException("<if>标签中的内容不能为空");
		}
		String expression = node.getAttributes().getNamedItem("test").getNodeValue();
		if(StringUtil.isEmpty(expression)) {
			throw new MetadataValidateException("<if>标签中的test属性值(即条件表达式)不能为空");
		}
		return new IfNodeMetadata(expression, content);
	}

	@Override
	public String getNodeName() {
		return "if";
	}
}
