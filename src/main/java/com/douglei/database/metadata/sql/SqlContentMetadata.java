package com.douglei.database.metadata.sql;

import com.douglei.database.dialect.DialectType;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataType;

/**
 * sql内容元数据
 * @author DougLei
 */
public class SqlContentMetadata implements Metadata{
	
	private DialectType dialect;
	private String content;
	
	public SqlContentMetadata(DialectType dialect, String content) {
		this.dialect = dialect;
		this.content = content;
	}

	@Override
	public String getCode() {
		return dialect.getCode();
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL_CONTENT;
	}
}
