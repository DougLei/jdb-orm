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
	private DialectType dialect;
	private SqlContentType type;
	
	private List<SqlNode> rootSqlNodes;
	
	public SqlContentMetadata(DialectType dialect, SqlContentType type) {
		this.dialect = dialect;
		this.type = type;
	}
	
	public void addRootSqlNode(SqlNode rootSqlNode) {
		if(rootSqlNodes == null) {
			rootSqlNodes = new ArrayList<SqlNode>();
		}
		rootSqlNodes.add(rootSqlNode);
	}
	
	
	public DialectType getDialect() {
		return dialect;
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
		return dialect.name();
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL_CONTENT;
	}
}
