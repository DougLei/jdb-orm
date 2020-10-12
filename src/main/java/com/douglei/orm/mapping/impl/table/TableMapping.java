package com.douglei.orm.mapping.impl.table;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.metadata.Metadata;
import com.douglei.orm.mapping.type.MappingTypeConstants;

/**
 * table 映射
 * @author DougLei
 */
public class TableMapping extends Mapping {
	private static final long serialVersionUID = 1938071340233722269L;

	public TableMapping(Metadata metadata) {
		super(MappingTypeConstants.TABLE, metadata);
	}
}
