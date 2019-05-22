package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node;

import com.douglei.database.metadata.MetadataValidate;

/**
 * 
 * @author DougLei
 */
public interface NodeMetadataValidate extends MetadataValidate{

	/**
	 * @see org.w3c.dom.Node.getNodeName()
	 * @return
	 */
	String getNodeName();
}
