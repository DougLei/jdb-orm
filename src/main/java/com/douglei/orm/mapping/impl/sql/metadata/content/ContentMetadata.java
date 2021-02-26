package com.douglei.orm.mapping.impl.sql.metadata.content;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.MappingParserContext;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.metadata.Metadata;

/**
 * 
 * @author DougLei
 */
public class ContentMetadata implements Metadata{
	
	protected String name;
	protected ContentType type;
	private IncrementIdValueConfig incrementIdValueConfig;
	private List<SqlNode> sqlNodes;
	
	public ContentMetadata(String name, ContentType type, IncrementIdValueConfig incrementIdValueConfig) {
		this.name = name;
		this.type = type;
		this.incrementIdValueConfig = incrementIdValueConfig;
	}

	public void addSqlNode(SqlNode sqlNode) {
		if(sqlNodes == null) 
			sqlNodes = new ArrayList<SqlNode>();
		sqlNodes.add(sqlNode);
	}
	
	@Override
	public final boolean equals(Object obj) {
		return name.equals(((ContentMetadata)obj).name);
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
