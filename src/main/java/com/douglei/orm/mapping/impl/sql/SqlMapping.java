package com.douglei.orm.mapping.impl.sql;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.MappingProperty;
import com.douglei.orm.mapping.metadata.AbstractMetadata;
import com.douglei.orm.metadata.type.MetadataTypeNameConstants;

/**
 * 
 * @author DougLei
 */
public class SqlMapping extends Mapping {
	private static final long serialVersionUID = -671186402591634974L;

	public SqlMapping(AbstractMetadata metadata, MappingProperty property) {
		super(MetadataTypeNameConstants.SQL, metadata, property);
	}
}
