package com.douglei.orm.mapping.impl.table;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.metadata.AbstractMetadata;
import com.douglei.orm.mapping.type.MappingTypeConstants;

/**
 * table 映射
 * @author DougLei
 */
public class TableMapping extends Mapping {
	private static final long serialVersionUID = 3858630569886995529L;

	public TableMapping(AbstractMetadata metadata) {
		super(MappingTypeConstants.TABLE, metadata);
	}
}
