package com.douglei.sessions.session.sql.impl.persistent.execution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.database.metadata.sql.SqlContentMetadata;
import com.douglei.database.metadata.sql.content.node.ExecuteSqlNode;
import com.douglei.database.metadata.sql.content.node.SqlNode;

/**
 * 要执行的sql实体
 * @author DougLei
 */
public class ExecuteSql {
	private String content;
	private List<Object> parameters;
	
	public ExecuteSql(SqlContentMetadata contentMetadata, Map<String, Object> sqlParameterMap) {
		StringBuilder sqlContent = new StringBuilder();
		
		List<SqlNode> sqlNodes = contentMetadata.getSqlNodes();
		
		ExecuteSqlNode executeSqlNode = null;
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.isMatching(sqlParameterMap)) {
				executeSqlNode = sqlNode.getExecuteSqlNode(sqlParameterMap);
				if(executeSqlNode.existsParameter()) {
					if(parameters == null) {
						parameters = new ArrayList<Object>();
					}
					parameters.addAll(executeSqlNode.getParameters());
				}
				sqlContent.append(executeSqlNode.getContent()).append(" ");
			}
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
