package com.douglei.orm.mapping.impl.table;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.metadata.Metadata;
import com.douglei.orm.mapping.type.MappingTypeNameConstants;

/**
 * table 映射
 * @author DougLei
 */
public class TableMapping extends Mapping {

	public TableMapping(Metadata metadata) {
		super(MappingTypeNameConstants.TABLE, metadata);
	}
}
