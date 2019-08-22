package com.douglei.orm.configuration.impl.xml.element.environment.mapping.sql.validate.content.node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.tools.instances.scanner.ClassScanner;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class SqlNodeHandlerMapping {
	private static Map<String, SqlNodeHandler> SQL_NODE_HANDLER_MAPPING;
	static {
		ClassScanner cs = new ClassScanner();
		List<String> classes = cs.scan(SqlNodeHandlerMapping.class.getPackage().getName() + ".impl");
		SQL_NODE_HANDLER_MAPPING = new HashMap<String, SqlNodeHandler>(classes.size());
		
		Object obj = null;
		SqlNodeHandler sqlNodeHandler = null;
		for (String cp : classes) {
			obj = ConstructorUtil.newInstance(cp);
			if(obj instanceof SqlNodeHandler) {
				sqlNodeHandler= (SqlNodeHandler) obj;
				SQL_NODE_HANDLER_MAPPING.put(sqlNodeHandler.getNodeName(), sqlNodeHandler);
			}
		}
		cs.destroy();
	}
	
	public static SqlNode doHandler(Node node) {
		if(node.getNodeType() != Node.COMMENT_NODE) {
			SqlNodeHandler sqlNodeHandler = SQL_NODE_HANDLER_MAPPING.get(node.getNodeName());
			if(sqlNodeHandler == null) {
				throw new NullPointerException("目前系统不支持nodeName=["+node.getNodeName()+"]的元素");
			}
			return (SqlNode) sqlNodeHandler.doHandler(node);
		}
		return null;
	}
}
