package com.douglei.database.metadata.sql;

import java.util.HashMap;
import java.util.Map;

import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataType;

/**
 * sql元数据
 * @author DougLei
 */
public class SqlMetadata implements Metadata{
	
	private String name;
	private Map<String, SqlContentMetadata> contents;
	
	public SqlMetadata(String name) {
		this.name = name;
	}
	
	/**
	 * 初始化sql content的长度
	 * @param size
	 */
	public void initialContentSize(int size) {
		contents = new HashMap<String, SqlContentMetadata>(size);
	}
	
	/**
	 * 添加 sql content
	 * @param sqlContentMetadata
	 */
	public void addContentMetadata(SqlContentMetadata sqlContentMetadata) {
		String sqlContentCode = sqlContentMetadata.getCode();
		if(!contents.containsKey(sqlContentCode)) {
			contents.put(sqlContentCode, sqlContentMetadata);
		}
	}

	/**
	 * name必须唯一
	 */
	@Override
	public String getCode() {
		return name;
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL;
	}
}
