package com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.IncrementIdValueConfig;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecuteSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;

/**
 * 要执行的sql实体
 * @author DougLei
 */
public class ExecuteSql {
	private String content;
	private List<Object> parameters;
	private IncrementIdValueConfig incrementIdValueConfig;
	
	public ExecuteSql(ContentMetadata contentMetadata, Object sqlParameter) {
		StringBuilder sqlContent = new StringBuilder();
		
		List<SqlNode> rootSqlNodes = contentMetadata.getRootSqlNodes();
		
		ExecuteSqlNode rootExecuteSqlNode = null;
		for (SqlNode rootSqlNode : rootSqlNodes) {
			if(rootSqlNode.matching(sqlParameter)) {
				rootExecuteSqlNode = rootSqlNode.getExecuteSqlNode(sqlParameter);
				if(rootExecuteSqlNode.existsParameter()) {
					if(parameters == null) 
						parameters = new ArrayList<Object>();
					parameters.addAll(rootExecuteSqlNode.getParameters());
				}
				sqlContent.append(rootExecuteSqlNode.getContent()).append(" ");
			}
		}
		
		// 记录sql语句
		this.content = sqlContent.toString();
		this.incrementIdValueConfig = contentMetadata.getIncrementIdValueConfig();
	}
	
	public String getContent() {
		return content;
	}
	public List<Object> getParameters() {
		return parameters;
	}
	public IncrementIdValueConfig getIncrementIdValueConfig() {
		return incrementIdValueConfig;
	}
}
