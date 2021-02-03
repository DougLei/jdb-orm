package com.douglei.orm.mapping.impl.table;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.MappingProperty;
import com.douglei.orm.mapping.metadata.AbstractMetadata;
import com.douglei.orm.metadata.type.MetadataTypeNameConstants;

/**
 * table 映射
 * @author DougLei
 */
public class TableMapping extends Mapping {
	private static final long serialVersionUID = -6678547313438215790L;

	public TableMapping(AbstractMetadata metadata, MappingProperty property) {
		super(MetadataTypeNameConstants.TABLE, metadata, property);
	}
}
