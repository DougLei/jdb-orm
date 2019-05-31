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
	private String dialectTypeCode;
	private SqlContentType type;
	
	private List<SqlNode> rootSqlNodes;
	
	public SqlContentMetadata(DialectType dialectType, SqlContentType type) {
		this.dialectTypeCode = dialectType.getCode();
		this.type = type;
	}
	
	public void addRootSqlNode(SqlNode rootSqlNode) {
		if(rootSqlNodes == null) {
			rootSqlNodes = new ArrayList<SqlNode>();
		}
		rootSqlNodes.add(rootSqlNode);
	}
	
	public SqlContentType getType() {
		return type;
	}
	public List<SqlNode> getRootSqlNodes() {
		return rootSqlNodes;
	}
	
	/**
	 * 即dialectType的code, 用来区分不同dialect, 调用不同的sql语句
	 */
	@Override
	public String getCode() {
		return dialectTypeCode;
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL_CONTENT;
	}
}
