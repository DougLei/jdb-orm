package com.douglei.database.metadata.sql;

import java.util.ArrayList;
import java.util.List;

import com.douglei.database.dialect.DialectType;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataType;
import com.douglei.database.metadata.sql.content.node.NodeMetadata;

/**
 * sql内容元数据
 * @author DougLei
 */
public class SqlContentMetadata implements Metadata{
	private String dialectTypeCode;
	private Type type;
	
	private List<NodeMetadata> nodes;
	
	public SqlContentMetadata(DialectType dialectType, Type type) {
		this.dialectTypeCode = dialectType.getCode();
		this.type = type;
	}
	
	public void addNodeMetadata(NodeMetadata node) {
		if(node == null) {
			return;
		}
		if(nodes == null) {
			nodes = new ArrayList<NodeMetadata>();
		}
		nodes.add(node);
	}
	
	public Type getType() {
		return type;
	}
	public List<NodeMetadata> getNodes() {
		return nodes;
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
