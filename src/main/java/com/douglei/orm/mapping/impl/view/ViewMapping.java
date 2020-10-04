package com.douglei.orm.mapping.impl.view;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.metadata.Metadata;
import com.douglei.orm.mapping.type.MappingTypeNameConstants;

/**
 * view 映射
 * @author DougLei
 */
public class ViewMapping extends Mapping {

	public ViewMapping(Metadata metadata) {
		super(MappingTypeNameConstants.VIEW, metadata);
	}
}
