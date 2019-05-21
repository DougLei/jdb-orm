package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node;

import com.douglei.database.metadata.MetadataValidate;

/**
 * 
 * @author DougLei
 */
public interface NodeMetadataValidate extends MetadataValidate{

	/**
	 * 获取节点类型
	 * @see org.w3c.dom.Node
	 * @return
	 */
	short getNodeType();
}
