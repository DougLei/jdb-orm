package com.douglei.database.metadata.sql;

import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataType;

/**
 * sql参数元数据
 * @author DougLei
 */
public class SqlParameterMetadata implements Metadata{

	@Override
	public String getCode() {
		return null;
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL_PARAMETER;
	}
}
