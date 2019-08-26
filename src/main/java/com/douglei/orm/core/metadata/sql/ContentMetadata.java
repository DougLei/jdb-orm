package com.douglei.orm.core.metadata.sql;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.context.xml.MappingXmlConfigContext;
import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataType;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;

/**
 * content元数据
 * @author DougLei
 */
public class ContentMetadata implements Metadata{
	protected String name;
	protected ContentType type;
	protected DialectType[] dialectTypes;
	
	private List<SqlNode> rootSqlNodes;
	
	public ContentMetadata(String name, DialectType[] dialectTypes) {
		this.name = name;
		this.type = MappingXmlConfigContext.getContentType();
		this.dialectTypes = dialectTypes;
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
