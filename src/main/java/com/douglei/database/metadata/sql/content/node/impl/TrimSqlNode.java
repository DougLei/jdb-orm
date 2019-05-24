package com.douglei.database.metadata.sql.content.node.impl;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import com.douglei.database.metadata.sql.content.node.ExecuteSqlNode;
import com.douglei.database.metadata.sql.content.node.SqlNode;
import com.douglei.database.metadata.sql.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class TrimSqlNode extends AbstractNestingNode {
	private TextSqlNode prefixAttributeNode;
	private TextSqlNode suffixAttributeNode;
	
	private String[] prefixoverride;
	private String[] suffixoverride;
	
	// 给子类使用的构造函数
	protected TrimSqlNode(String prefix, String suffix, String prefixoverride, String suffixoverride) {
		if(prefix != null) {
			this.prefixAttributeNode = new TextSqlNode(prefix);
			addSqlNode(prefixAttributeNode);
		}
		if(suffix != null) {
			this.suffixAttributeNode = new TextSqlNode(suffix);
			addSqlNode(suffixAttributeNode);
		}
		if(prefixoverride != null) {
			this.prefixoverride = prefixoverride.split("|");
		}
		if(suffixoverride != null) {
			this.suffixoverride = suffixoverride.split("|");
		}
	}
	public TrimSqlNode(Node prefix, Node suffix, Node prefixoverride, Node suffixoverride) {
		if(prefix != null) {
			this.prefixAttributeNode = new TextSqlNode(prefix.getNodeValue());
			addSqlNode(prefixAttributeNode);
		}
		if(suffix != null) {
			this.suffixAttributeNode = new TextSqlNode(suffix.getNodeValue());
			addSqlNode(suffixAttributeNode);
		}
		if(prefixoverride != null) {
			this.prefixoverride = prefixoverride.getNodeValue().split("|");
		}
		if(suffixoverride != null) {
			this.suffixoverride = suffixoverride.getNodeValue().split("|");
		}
	}
	
	@Override
	public boolean matching(Object sqlParameter) {
		return true;
	}
	
	@Override
	public ExecuteSqlNode getExecuteSqlNode(Object sqlParameter, String sqlParameterNamePrefix) {
		List<String> sqlContents = null;
		List<Object> parameters = null;
		
		ExecuteSqlNode executeSqlNode = null;
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameter)) {
				executeSqlNode = sqlNode.getExecuteSqlNode(sqlParameter);
				if(executeSqlNode.existsParameter()) {
					if(parameters == null) {
						parameters = new ArrayList<Object>();
					}
					parameters.addAll(executeSqlNode.getParameters());
				}
				if(sqlContents == null) {
					sqlContents = new ArrayList<String>();
				}
				sqlContents.add(executeSqlNode.getContent());
			}
		}
		
		StringBuilder sqlContent = new StringBuilder();
		String tmpSqlContent = null;
		int index=0, length=sqlContents.size();
		boolean unProcessPrefixoverride = prefixoverride != null, unProcessSuffixoverride = suffixoverride != null;
		while(index < length) {
			tmpSqlContent = sqlContents.get(index);
			if(unProcessPrefixoverride) {
				if((prefixAttributeNode == null && index == 0) || (prefixAttributeNode != null && index == 1)) {
					for (String po : prefixoverride) {
						if(po.equalsIgnoreCase(tmpSqlContent.substring(0, po.length()))) {
							tmpSqlContent = tmpSqlContent.substring(0, po.length());
							break;
						}
					}
					unProcessPrefixoverride = false;
				}
			}
			
			if(unProcessSuffixoverride) {
				if((suffixAttributeNode == null && index == length-1) || (suffixAttributeNode != null && index == length-2)) {
					for (String so : suffixoverride) {
						if(so.equalsIgnoreCase(tmpSqlContent.substring(tmpSqlContent.length()-so.length()))) {
							tmpSqlContent = tmpSqlContent.substring(0, tmpSqlContent.length()-so.length());
							break;
						}
					}
					unProcessSuffixoverride = false;
				}
			}
			sqlContent.append(tmpSqlContent).append(" ");
			index++;
		}
		return new ExecuteSqlNode(sqlContent.toString(), parameters);
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.TRIM;
	}
}