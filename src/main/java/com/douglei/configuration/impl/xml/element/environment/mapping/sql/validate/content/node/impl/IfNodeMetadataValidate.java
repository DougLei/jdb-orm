package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.impl;

import org.w3c.dom.Node;

import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.NodeMetadataValidate;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataValidateException;
import com.douglei.database.metadata.sql.content.node.impl.TextNodeMetadata;
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
		String content = node.getNodeValue();
		if(StringUtil.isEmpty(content)) {
			return null;
		}
		return new TextNodeMetadata(content);
	}

	@Override
	public String getNodeName() {
		return "if";
	}
}
