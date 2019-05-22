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
		
		List<SqlNode> rootSqlNodes = contentMetadata.getRootSqlNodes();
		
		ExecuteSqlNode rootExecuteSqlNode = null;
		for (SqlNode rootSqlNode : rootSqlNodes) {
			if(rootSqlNode.matching(sqlParameterMap)) {
				rootExecuteSqlNode = rootSqlNode.getExecuteSqlNode(sqlParameterMap);
				if(rootExecuteSqlNode.existsParameter()) {
					if(parameters == null) {
						parameters = new ArrayList<Object>();
					}
					parameters.addAll(rootExecuteSqlNode.getParameters());
				}
				sqlContent.append(rootExecuteSqlNode.getContent()).append(" ");
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
