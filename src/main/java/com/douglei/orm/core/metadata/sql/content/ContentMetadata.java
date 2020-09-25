package com.douglei.orm.core.metadata.sql.content;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.configuration.impl.element.environment.mapping.MappingResolverContext;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;

/**
 * content元数据
 * @author DougLei
 */
public class ContentMetadata implements Metadata{
	private static final long serialVersionUID = -480535121424450166L;
	protected String name;
	protected ContentType type;
	private IncrementIdValueConfig incrementIdValueConfig;
	
	private List<SqlNode> rootSqlNodes;
	
	public ContentMetadata(String name, IncrementIdValueConfig incrementIdValueConfig) {
		this.name = name;
		this.incrementIdValueConfig = incrementIdValueConfig;
		this.type = MappingResolverContext.getCurrentSqlType();
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
	
	/**
	 * 不需要唯一编码值
	 */
	@Deprecated
	@Override
	public String getCode() {
		return name;
	}
}
