package com.douglei.core.metadata.sql;

import java.util.ArrayList;
import java.util.List;

import com.douglei.core.dialect.DialectType;
import com.douglei.core.metadata.Metadata;
import com.douglei.core.metadata.MetadataType;
import com.douglei.core.metadata.sql.content.node.SqlNode;

/**
 * sql内容元数据
 * @author DougLei
 */
public class SqlContentMetadata implements Metadata{
	private DialectType dialectType;
	private SqlContentType type;
	
	private List<SqlNode> rootSqlNodes;
	
	public SqlContentMetadata(DialectType dialectType, SqlContentType type) {
		this.dialectType = dialectType;
		this.type = type;
	}
	
	public void addRootSqlNode(SqlNode rootSqlNode) {
		if(rootSqlNodes == null) {
			rootSqlNodes = new ArrayList<SqlNode>();
		}
		rootSqlNodes.add(rootSqlNode);
	}
	
	
	public DialectType getDialectType() {
		return dialectType;
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
		return dialectType.name();
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL_CONTENT;
	}
}
