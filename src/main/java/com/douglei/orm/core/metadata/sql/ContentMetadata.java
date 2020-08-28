package com.douglei.orm.core.metadata.sql;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.configuration.impl.element.environment.mapping.MappingResolverContext;
import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataType;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;

/**
 * content元数据
 * @author DougLei
 */
public class ContentMetadata implements Metadata{
	private static final long serialVersionUID = -8187127309286994470L;
	protected String name;
	protected DialectType[] dialectTypes;
	protected ContentType type;
	private IncrementIdValueConfig incrementIdValueConfig;
	
	private List<SqlNode> rootSqlNodes;
	
	public ContentMetadata(String name, DialectType[] dialectTypes, IncrementIdValueConfig incrementIdValueConfig) {
		this.name = name;
		this.dialectTypes = dialectTypes;
		this.incrementIdValueConfig = incrementIdValueConfig;
		this.type = MappingResolverContext.getCurrentSqlType();
	}

	public void addRootSqlNode(SqlNode rootSqlNode) {
		if(rootSqlNodes == null) {
			rootSqlNodes = new ArrayList<SqlNode>();
		}
		rootSqlNodes.add(rootSqlNode);
	}
	
	public boolean isMatchingDialectType(DialectType currentDialectType) {
		for (DialectType dt : dialectTypes) {
			if(dt == currentDialectType) {
				return true;
			}
		}
		return false;
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
	
	@Deprecated
	@Override
	public String getCode() {
		return null;
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.CONTENT;
	}
}
