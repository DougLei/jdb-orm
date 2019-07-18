package com.douglei.orm.core.metadata.sql;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataType;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;

/**
 * sql内容元数据
 * @author DougLei
 */
public class SqlContentMetadata implements Metadata{
	private static final long serialVersionUID = -3887183011478334184L;
	
	private DialectType[] dialectTypes;
	private SqlContentType type;
	
	private List<SqlNode> rootSqlNodes;
	
	public SqlContentMetadata(DialectType[] dialectTypes, SqlContentType type) {
		this.dialectTypes = dialectTypes;
		this.type = type;
	}
	
	public void addRootSqlNode(SqlNode rootSqlNode) {
		if(rootSqlNodes == null) {
			rootSqlNodes = new ArrayList<SqlNode>();
		}
		rootSqlNodes.add(rootSqlNode);
	}
	
	public boolean isMatchingDialectType(DialectType dialect) {
		for (DialectType dt : dialectTypes) {
			if(dt == dialect) {
				return true;
			}
		}
		return false;
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
