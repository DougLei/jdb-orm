package com.douglei.orm.mapping.impl.sql.metadata.parser.content.node;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.tools.file.scanner.impl.ClassScanner;
import com.douglei.tools.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class SqlNodeParserContainer {
	private static Map<String, SqlNodeParser> container = new HashMap<String, SqlNodeParser>();
	static {
		SqlNodeParser sqlNodeHandler = null;
		for (String classpath : new ClassScanner().scan(SqlNodeParserContainer.class.getPackage().getName() + ".impl")) {
			sqlNodeHandler= (SqlNodeParser) ConstructorUtil.newInstance(classpath);
			container.put(sqlNodeHandler.getNodeType().getNodeName(), sqlNodeHandler);
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
				throw new IllegalArgumentException("目前系统不支持解析name为["+node.getNodeName()+"]的xml标签");
			
			return (SqlNode) sqlNodeHandler.parse(node);
		}
		return null;
	}
}
