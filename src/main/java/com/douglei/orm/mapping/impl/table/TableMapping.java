package com.douglei.orm.mapping.impl.table;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.MappingTypeNameConstants;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;

/**
 * table 映射
 * @author DougLei
 */
public class TableMapping extends Mapping {

	public TableMapping(TableMetadata metadata) {
		super(MappingTypeNameConstants.TABLE, metadata);
	}
}
