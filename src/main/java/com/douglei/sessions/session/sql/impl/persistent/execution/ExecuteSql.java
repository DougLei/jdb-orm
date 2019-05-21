package com.douglei.sessions.session.sql.impl.persistent.execution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.database.metadata.sql.SqlContentMetadata;
import com.douglei.database.metadata.sql.content.node.NodeMetadata;

/**
 * 要执行的sql实体
 * @author DougLei
 */
public class ExecuteSql {
	private String content;
	private List<Object> parameters;
	
	public ExecuteSql(SqlContentMetadata content, Map<String, Object> sqlParameterMap) {
		List<NodeMetadata> nodes = content.getNodes();
		int nodeLength = nodes.size();
		
		StringBuilder sqlContent = new StringBuilder(nodeLength*120);
		parameters = new ArrayList<Object>(nodeLength*5);
		
		for (NodeMetadata node : nodes) {
			
		}
		
		// 记录sql语句
		this.content = sqlContent.toString();
	}
	
	public String getContent() {
		return content;
	}
	public List<Object> getParameters() {
		return parameters;
	}
}
