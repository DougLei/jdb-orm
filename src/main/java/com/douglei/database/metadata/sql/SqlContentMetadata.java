package com.douglei.database.metadata.sql;

import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataType;

/**
 * sql内容元数据
 * @author DougLei
 */
public class SqlContentMetadata implements Metadata{

	@Override
	public String getCode() {
		return null;
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL_CONTENT;
	}
}
