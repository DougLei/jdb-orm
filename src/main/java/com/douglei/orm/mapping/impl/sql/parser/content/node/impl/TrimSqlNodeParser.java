package com.douglei.orm.mapping.impl.sql.parser.content.node.impl;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.TrimSqlNode;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParseException;

/**
 * 
 * @author DougLei
 */
public class TrimSqlNodeParser extends SqlNodeParser {

	@Override
	public SqlNode parse(Node node) throws SqlNodeParseException {
		NamedNodeMap attributeMap = node.getAttributes();
		
		// 解析 prefix
		String prefix = getAttributeValue(attributeMap.getNamedItem("prefix"));
		prefix = prefix==null?" ":prefix+" ";
		
		// 解析 suffix
		String suffix = getAttributeValue(attributeMap.getNamedItem("suffix"));
		suffix = suffix==null?" ":" "+suffix;
		
		// 解析 prefixOverride
		String[] prefixOverrideArray = null;
		String prefixOverride = getAttributeValue(attributeMap.getNamedItem("prefixOverride"));
		if(prefixOverride != null) {
			String[] tmp = prefixOverride.split("\\|");
			int length = tmp.length;
			prefixOverrideArray = new String[length];
			for (int i = 0; i < length; i++) {
				prefixOverrideArray[i] = tmp[i];
			}
		}
		
		// 解析 suffixOverride
		String[] suffixOverrideArray = null;
		String suffixOverride = getAttributeValue(attributeMap.getNamedItem("suffixOverride"));
		if(suffixOverride != null) {
			String[] tmp = suffixOverride.split("\\|");
			int length = tmp.length;
			suffixOverrideArray = new String[length];
			for (int i = 0; i < length; i++) {
				suffixOverrideArray[i] = tmp[i];
			}
		}
		
		TrimSqlNode trimSqlNode = new TrimSqlNode(prefix, suffix, prefixOverrideArray, suffixOverrideArray);
		setChildrenSqlNodes(trimSqlNode, node);
		return trimSqlNode;
	}
	
	@Override
	protected void validateChildrenSqlNode(SqlNode children) throws SqlNodeParseException {
		if(children.getType() != SqlNodeType.IF)
			throw new SqlNodeParseException("<trim>中, 只能使用<if>; 也不支持在<trim>中, 直接编写sql语句");
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.TRIM;
	}
}
