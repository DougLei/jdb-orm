package com.douglei.orm.mapping.impl.sql;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.metadata.Metadata;
import com.douglei.orm.mapping.type.MappingTypeConstants;

/**
 * 
 * @author DougLei
 */
public class SqlMapping extends Mapping {

	public SqlMapping(Metadata metadata) {
		super(MappingTypeConstants.SQL, metadata);
	}
}
