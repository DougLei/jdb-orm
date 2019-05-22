package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node;

import org.w3c.dom.Node;

import com.douglei.database.metadata.sql.content.node.SqlNode;

/**
 * 
 * @author DougLei
 */
public interface SqlNodeHandler {

	/**
	 * 
	 * @param node
	 * @return
	 */
	SqlNode doHandler(Node node);
	
	/**
	 * @see org.w3c.dom.Node.getNodeName()
	 * @return
	 */
	String getNodeName();
}
