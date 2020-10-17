package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;

/**
 * 
 * @author DougLei
 */
public class TextSqlNode extends AbstractSqlNode {
	private static final long serialVersionUID = -3598936871821754634L;

	public TextSqlNode(String content) {
		super(content);
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.TEXT;
	}
	
	public String getContent() {
		return content;
	}
	
	/**
	 * 获取sql参数, 按照配置中定义的顺序记录
	 * @return
	 */
	public List<SqlParameterMetadata> getSqlParameters(){
		return sqlParameters;
	}
}
