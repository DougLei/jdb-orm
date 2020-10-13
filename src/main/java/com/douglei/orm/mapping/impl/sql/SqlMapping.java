package com.douglei.orm.mapping.impl.sql;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.metadata.AbstractMetadata;
import com.douglei.orm.mapping.type.MappingTypeConstants;

/**
 * 
 * @author DougLei
 */
public class SqlMapping extends Mapping {
	private static final long serialVersionUID = 686767351837402752L;

	public SqlMapping(AbstractMetadata metadata) {
		super(MappingTypeConstants.SQL, metadata);
	}
}
