package com.douglei.database.metadata.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataType;

/**
 * sql元数据
 * @author DougLei
 */
public class SqlMetadata implements Metadata{
	
	private String name;
	private Map<String, List<SqlContentMetadata>> contentMap;
	
	public SqlMetadata(String name) {
		this.name = name;
	}
	
	/**
	 * 添加 sql content
	 * @param sqlContentMetadata
	 */
	public void addContentMetadata(SqlContentMetadata sqlContentMetadata) {
		if(contentMap == null) {
			contentMap = new HashMap<String, List<SqlContentMetadata>>(3);
		}
		
		String dialectTypeCode = sqlContentMetadata.getCode();
		List<SqlContentMetadata> contents = contentMap.get(dialectTypeCode);
		if(contents == null) {
			contents = new ArrayList<SqlContentMetadata>(4);
			contentMap.put(dialectTypeCode, contents);
		}
		contents.add(sqlContentMetadata);
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
