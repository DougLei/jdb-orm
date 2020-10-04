package com.douglei.orm.mapping.impl.sql.metadata.parser.content.node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.tools.instances.resource.scanner.impl.ClassScanner;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class SqlNodeParserContainer {
	private static Map<String, SqlNodeParser> container = new HashMap<String, SqlNodeParser>();
	static {
		ClassScanner scanner = new ClassScanner();
		List<String> classes = scanner.scan(SqlNodeParserContainer.class.getPackage().getName() + ".impl");
		
		SqlNodeParser sqlNodeHandler = null;
		for (String cp : classes) {
			sqlNodeHandler= (SqlNodeParser) ConstructorUtil.newInstance(cp);
			container.put(sqlNodeHandler.getNodeName(), sqlNodeHandler);
		}
	}
	
	/**
	 * 解析sql node
	 * @param node
	 * @return
	 */
	public static SqlNode parse(Node node) {
		if(node.getNodeType() != Node.COMMENT_NODE) {
			SqlNodeParser sqlNodeHandler = container.get(node.getNodeName());
			if(sqlNodeHandler == null) 
				throw new IllegalArgumentException("目前系统不支持解析name为"+node.getNodeName()+"的xml标签");
			
			return (SqlNode) sqlNodeHandler.parse(node);
		}
		return null;
	}
}
