package com.douglei.orm.core.metadata.sql;

import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.metadata.MetadataType;

/**
 * sql-content元数据
 * @author DougLei
 */
public class SqlContentMetadata extends ContentMetadata{
	public SqlContentMetadata(String name, DialectType[] dialectTypes) {
		super(name, dialectTypes);
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL_CONTENT;
	}
}
