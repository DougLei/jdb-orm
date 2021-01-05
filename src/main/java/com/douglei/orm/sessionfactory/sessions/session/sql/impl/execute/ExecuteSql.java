package com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.content.IncrementIdValueConfig;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecuteSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class ExecuteSql {
	private String name;
	private ContentType type;
	private String content;
	private List<SqlParameterMetadata> parameters; // sql参数集合
	private List<Object> parameterValues; // 执行sql语句时的参数值集合
	private IncrementIdValueConfig incrementIdValueConfig; // 自增id值的配置, 用在insert类型的sql语句
	
	public ExecuteSql(PurposeEntity purposeEntity, ContentMetadata contentMetadata, Object sqlParameter) {
		StringBuilder sqlContent = new StringBuilder();
		
		List<SqlNode> sqlNodes = contentMetadata.getSqlNodes();
		
		ExecuteSqlNode rootExecuteSqlNode = null;
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameter)) {
				rootExecuteSqlNode = sqlNode.getExecuteSqlNode(purposeEntity, sqlParameter);
				if(rootExecuteSqlNode.existsParameters()) {
					if(parameters == null) 
						parameters = new ArrayList<SqlParameterMetadata>();
					parameters.addAll(rootExecuteSqlNode.getParameters());
				}
				if(rootExecuteSqlNode.existsParameterValues()) {
					if(parameterValues == null)
						parameterValues = new ArrayList<Object>();
					parameterValues.addAll(rootExecuteSqlNode.getParameterValues());
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
	public List<SqlParameterMetadata> getParameters() {
		return parameters;
	}
	public List<Object> getParameterValues() {
		return parameterValues;
	}
	public IncrementIdValueConfig getIncrementIdValueConfig() {
		return incrementIdValueConfig;
	}
}
