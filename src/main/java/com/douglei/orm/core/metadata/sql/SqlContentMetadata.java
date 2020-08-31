package com.douglei.orm.core.metadata.sql;

import com.douglei.orm.core.metadata.MetadataType;

/**
 * sql-content元数据
 * @author DougLei
 */
public class SqlContentMetadata extends ContentMetadata{
	private static final long serialVersionUID = 2620136088145724869L;

	public SqlContentMetadata(String name, IncrementIdValueConfig incrementIdValueConfig) {
		super(name, incrementIdValueConfig);
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL_CONTENT;
	}
}
