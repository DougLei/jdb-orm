package com.douglei.orm.mapping.impl.table;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.metadata.AbstractMetadata;
import com.douglei.orm.mapping.type.MappingTypeConstants;

/**
 * table 映射
 * @author DougLei
 */
public class TableMapping extends Mapping {

	public TableMapping(AbstractMetadata metadata) {
		super(MappingTypeConstants.TABLE, metadata);
	}
}
