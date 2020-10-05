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
	protected String name;
	protected ContentType type;
	private IncrementIdValueConfig incrementIdValueConfig;
	
	private List<SqlNode> rootSqlNodes;
	
	public ContentMetadata(String name, IncrementIdValueConfig incrementIdValueConfig) {
		this.name = name;
		this.incrementIdValueConfig = incrementIdValueConfig;
		this.type = MappingParserContext.getCurrentSqlType();
	}

	public void addRootSqlNode(SqlNode rootSqlNode) {
		if(rootSqlNodes == null) {
			rootSqlNodes = new ArrayList<SqlNode>();
		}
		rootSqlNodes.add(rootSqlNode);
	}
	
	public String getName() {
		return name;
	}
	public ContentType getType() {
		return type;
	}
	public List<SqlNode> getRootSqlNodes() {
		return rootSqlNodes;
	}
	public IncrementIdValueConfig getIncrementIdValueConfig() {
		return incrementIdValueConfig;
	}
}
