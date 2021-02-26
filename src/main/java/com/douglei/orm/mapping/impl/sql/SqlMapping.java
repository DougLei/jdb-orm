package com.douglei.orm.mapping.impl.sql;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.MappingTypeNameConstants;
import com.douglei.orm.mapping.metadata.AbstractMetadata;

/**
 * 
 * @author DougLei
 */
public class SqlMapping extends Mapping {

	public SqlMapping(AbstractMetadata metadata) {
		super(MappingTypeNameConstants.SQL, metadata);
	}
}
