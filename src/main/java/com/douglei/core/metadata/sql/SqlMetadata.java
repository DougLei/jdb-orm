package com.douglei.core.metadata.sql;

import java.util.ArrayList;
import java.util.List;

import com.douglei.core.dialect.DialectType;
import com.douglei.core.metadata.Metadata;
import com.douglei.core.metadata.MetadataType;

/**
 * sql元数据
 * @author DougLei
 */
public class SqlMetadata implements Metadata{
	
	private String namespace;
	private String name;
	private List<SqlContentMetadata> contents;
	
	public SqlMetadata(String namespace, String name) {
		this.namespace = namespace;
		this.name = name;
	}

	/**
	 * 添加 sql content
	 * @param sqlContentMetadata
	 */
	public void addSqlContentMetadata(SqlContentMetadata sqlContentMetadata) {
		if(contents == null) {
			contents = new ArrayList<SqlContentMetadata>(10);
		}
		contents.add(sqlContentMetadata);
	}
	
	/**
	 * <pre>
	 * 	如果namespace为空, 返回name
	 * 	如果namespace不为空, 返回namespace.name
	 * </pre>
	 */
	@Override
	public String getCode() {
		if(namespace == null) {
			return name;
		}
		return namespace+"."+name;
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL;
	}
	
	public String getNamespace() {
		return namespace;
	}
	public String getName() {
		return name;
	}
	public List<SqlContentMetadata> getContents(DialectType dialect) {
		List<SqlContentMetadata> list = new ArrayList<SqlContentMetadata>(contents.size());
		for (SqlContentMetadata content : contents) {
			if(content.getDialectType() == dialect || content.getDialectType() == DialectType.ALL) {
				list.add(content);
			}
		}
		return list;
	}
}
