package com.douglei.orm.mapping.impl.sql.parser.content.node;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.tools.file.scanner.impl.ClassScanner;
import com.douglei.tools.reflect.ClassUtil;

/**
 * 
 * @author DougLei
 */
public class SqlNodeParserContainer {
	private static Map<String, SqlNodeParser> container = new HashMap<String, SqlNodeParser>();
	static {
		SqlNodeParser parser = null;
		for (String clazz : new ClassScanner().scan(SqlNodeParserContainer.class.getPackage().getName() + ".impl")) {
			parser= (SqlNodeParser) ClassUtil.newInstance(clazz);
			container.put(parser.getNodeType().getNodeName(), parser);
		}
	}
	
	/**
	 * 解析sql node
	 * @param node
	 * @return
	 */
	public static SqlNode parse(Node node) {
		if(node.getNodeType() == Node.COMMENT_NODE)
			return null;
		return (SqlNode)  container.get(node.getNodeName()).parse(node);
	}
}
