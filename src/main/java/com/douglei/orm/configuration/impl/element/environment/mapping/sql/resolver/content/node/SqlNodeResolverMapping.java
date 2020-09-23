package com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node;

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
public class SqlNodeResolverMapping {
	private static Map<String, SqlNodeResolver> SQL_NODE_RESOLVER_MAPPING = new HashMap<String, SqlNodeResolver>();
	static {
		ClassScanner cs = new ClassScanner();
		List<String> classes = cs.scan(SqlNodeResolverMapping.class.getPackage().getName() + ".impl");
		
		SqlNodeResolver sqlNodeHandler = null;
		for (String cp : classes) {
			sqlNodeHandler= (SqlNodeResolver) ConstructorUtil.newInstance(cp);
			SQL_NODE_RESOLVER_MAPPING.put(sqlNodeHandler.getNodeName(), sqlNodeHandler);
		}
		cs.destroy();
	}
	
	/**
	 * 解析sql node
	 * @param node
	 * @return
	 */
	public static SqlNode resolving(Node node) {
		if(node.getNodeType() != Node.COMMENT_NODE) {
			SqlNodeResolver sqlNodeHandler = SQL_NODE_RESOLVER_MAPPING.get(node.getNodeName());
			if(sqlNodeHandler == null) 
				throw new IllegalArgumentException("目前系统不支持解析name为"+node.getNodeName()+"的xml标签");
			
			return (SqlNode) sqlNodeHandler.resolving(node);
		}
		return null;
	}
}
