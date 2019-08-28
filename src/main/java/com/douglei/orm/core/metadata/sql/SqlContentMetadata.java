package com.douglei.orm.core.metadata.sql;

import com.douglei.orm.core.dialect.DialectType;
import com.douglei.orm.core.metadata.MetadataType;

/**
 * sql-content元数据
 * @author DougLei
 */
public class SqlContentMetadata extends ContentMetadata{
	private static final long serialVersionUID = -4872042176126975925L;

	public SqlContentMetadata(String name, DialectType[] dialectTypes) {
		super(name, dialectTypes);
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL_CONTENT;
	}
}
