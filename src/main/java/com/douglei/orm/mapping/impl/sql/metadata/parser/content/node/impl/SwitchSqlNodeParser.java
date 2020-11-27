package com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.impl;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.SwitchSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParserContainer;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParserException;

/**
 * 
 * @author DougLei
 */
public class SwitchSqlNodeParser implements SqlNodeParser {

	@Override
	public SqlNode parse(Node node) throws SqlNodeParserException {
		NodeList childrens = node.getChildNodes();
		int cl = childrens.getLength();
		if(cl == 0) 
			throw new SqlNodeParserException("<switch>元素中不存在任何元素配置");
		
		SwitchSqlNode switchSqlNode = new SwitchSqlNode();
		
		SqlNode c_sn = null;
		for(int i=0;i<cl;i++) {
			c_sn = SqlNodeParserContainer.parse(childrens.item(i));
			if(c_sn != null) {
				if(c_sn.getType() == SqlNodeType.IF || c_sn.getType() == SqlNodeType.ELSE) {
					switchSqlNode.addSqlNode(c_sn);
				}else {
					throw new SqlNodeParserException("<switch>元素中, 只能使用<if>元素或<else>元素; 也不支持在<switch>元素中, 直接编写sql语句");
				}
			}
		}
		if(switchSqlNode.existsSqlNode()) 
			return switchSqlNode;
		throw new SqlNodeParserException("<switch>元素中不存在任何元素配置");
	}
	
	@Override
	public String getNodeName() {
		return "switch";
	}
}
