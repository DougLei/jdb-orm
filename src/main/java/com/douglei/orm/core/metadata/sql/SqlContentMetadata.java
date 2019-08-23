package com.douglei.orm.core.metadata.sql;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.context.MappingConfigContext;
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
	private boolean defaultExecute;
	private SqlContentType type;
	private DialectType[] dialectTypes;
	
	private List<SqlNode> rootSqlNodes;
	
	public SqlContentMetadata(String name, boolean defaultExecute, DialectType[] dialectTypes) {
		this.name = name;
		this.defaultExecute = defaultExecute;
		this.type = MappingConfigContext.getCurrentSqlContentType();
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
	public boolean isDefaultExecute() {
		return defaultExecute;
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
