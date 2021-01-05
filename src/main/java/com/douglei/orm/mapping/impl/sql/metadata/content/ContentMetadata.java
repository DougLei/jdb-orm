package com.douglei.orm.mapping.impl.sql.metadata.content;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.MappingParserContext;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.metadata.Metadata;

/**
 * content元数据
 * @author DougLei
 */
public class ContentMetadata implements Metadata{
	private static final long serialVersionUID = 7918171070776891692L;
	
	protected String name;
	protected ContentType type;
	private IncrementIdValueConfig incrementIdValueConfig;
	private List<SqlNode> sqlNodes;
	
	public ContentMetadata(String name, IncrementIdValueConfig incrementIdValueConfig) {
		this.name = name;
		this.incrementIdValueConfig = incrementIdValueConfig;
		this.type = MappingParserContext.getCurrentSqlType();
	}

	public void addSqlNode(SqlNode sqlNode) {
		if(sqlNodes == null) {
			sqlNodes = new ArrayList<SqlNode>();
		}
		sqlNodes.add(sqlNode);
	}
	
	public String getName() {
		return name;
	}
	public ContentType getType() {
		return type;
	}
	public List<SqlNode> getSqlNodes() {
		return sqlNodes;
	}
	public IncrementIdValueConfig getIncrementIdValueConfig() {
		return incrementIdValueConfig;
	}
}
