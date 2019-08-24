package com.douglei.orm.core.metadata.sql;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.context.xml.MappingXmlConfigContext;
import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataType;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;

/**
 * sql内容元数据
 * @author DougLei
 */
public class SqlContentMetadata implements Metadata{
	private String name;
	private SqlContentType type;
	private DialectType[] dialectTypes;
	
	private List<SqlNode> rootSqlNodes;
	
	public SqlContentMetadata(String name, DialectType[] dialectTypes) {
		this.name = name;
		this.type = MappingXmlConfigContext.getSqlContentType();
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
	public SqlContentType getType() {
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
		return MetadataType.SQL_CONTENT;
	}
}
