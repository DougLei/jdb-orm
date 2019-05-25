package com.douglei.database.metadata.sql.content.node.impl;

import com.douglei.database.metadata.sql.content.node.SqlNodeType;
import com.douglei.instances.ognl.OgnlHandler;

/**
 * 
 * @author DougLei
 */
public class IfSqlNode extends AbstractSqlNode {
	private String expression;
	
	public IfSqlNode(String expression, String content) {
		super(content);
		this.expression = expression;
	}

	@Override
	public boolean matching(Object sqlParameter, String alias) {
		processExpression(alias);
		return OgnlHandler.singleInstance().getBooleanValue(expression, sqlParameter);
	}
	
	private boolean unProcessExpression = true;// 是否【没有】处理expression, 默认都没有处理
	private void processExpression(String alias) {
		if(unProcessExpression) {
			unProcessExpression = false;
			
			if(alias != null && expression.indexOf(alias+".") != -1) {
				expression = expression.replace(alias+".", "");
			}
		}
	}
	
	public static void main(String[] args) {
		String a = "user.id != null && user.name == null";
		System.out.println(a.replace("user.", ""));
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.IF;
	}
}