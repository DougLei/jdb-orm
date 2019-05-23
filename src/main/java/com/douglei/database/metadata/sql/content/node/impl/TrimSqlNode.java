package com.douglei.database.metadata.sql.content.node.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import com.douglei.database.metadata.sql.content.node.ExecuteSqlNode;
import com.douglei.database.metadata.sql.content.node.SqlNode;

/**
 * 
 * @author DougLei
 */
public class TrimSqlNode implements SqlNode {
	private TextSqlNode prefixAttributeNode;
	private TextSqlNode suffixAttributeNode;
	
	private String[] prefixoverride;
	private String[] suffixoverride;
	
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
	
	private List<SqlNode> sqlNodes;
	public void addSqlNode(SqlNode sqlNode) {
		if(sqlNode == null) {
			return;
		}
		if(sqlNodes == null) {
			sqlNodes = new ArrayList<SqlNode>();
		}
		sqlNodes.add(sqlNode);
	}
	
	@Override
	public boolean matching(Map<String, Object> sqlParameterMap) {
		return true;
	}
	
	@Override
	public ExecuteSqlNode getExecuteSqlNode(Map<String, Object> sqlParameterMap) {
		List<String> sqlContents = null;
		List<Object> parameters = null;
		
		ExecuteSqlNode executeSqlNode = null;
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameterMap)) {
				executeSqlNode = sqlNode.getExecuteSqlNode(sqlParameterMap);
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
						if(tmpSqlContent.startsWith(po)) {
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
						if(tmpSqlContent.endsWith(so)) {
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

	public boolean existsSqlNode() {
		return sqlNodes != null;
	}
}