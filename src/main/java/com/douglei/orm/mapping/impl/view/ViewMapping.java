package com.douglei.orm.mapping.impl.view;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.MappingTypeNameConstants;
import com.douglei.orm.mapping.metadata.AbstractMetadata;

/**
 * view 映射
 * @author DougLei
 */
public class ViewMapping extends Mapping {

	public ViewMapping(AbstractMetadata metadata) {
		super(MappingTypeNameConstants.VIEW, metadata);
	}
}
