package com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.content.IncrementIdValueConfig;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecuteSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;

/**
 * 要执行的sql实体
 * @author DougLei
 */
public class ExecuteSql {
	private String name;
	private ContentType type;
	private String content;
	private List<Object> parameters; // 执行sql语句对应的参数值集合
	private List<SqlParameterMetadata> sqlParameters; // sql参数集合
	private IncrementIdValueConfig incrementIdValueConfig;
	
	public ExecuteSql(PurposeEntity purposeEntity, ContentMetadata contentMetadata, Object sqlParameter) {
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
		this.name = contentMetadata.getName();
		this.type = contentMetadata.getType();
		this.incrementIdValueConfig = contentMetadata.getIncrementIdValueConfig();
	}
	
	public String getName() {
		return name;
	}
	public ContentType getType() {
		return type;
	}
	public String getContent() {
		return content;
	}
	public List<Object> getParameters() {
		return parameters;
	}
	public List<SqlParameterMetadata> getSqlParameters() {
		return sqlParameters;
	}
	public IncrementIdValueConfig getIncrementIdValueConfig() {
		return incrementIdValueConfig;
	}
}
