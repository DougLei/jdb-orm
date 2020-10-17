package com.douglei.orm.mapping.impl.view;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.metadata.AbstractMetadata;
import com.douglei.orm.mapping.type.MappingTypeConstants;

/**
 * view 映射
 * @author DougLei
 */
public class ViewMapping extends Mapping {
	private static final long serialVersionUID = -5605757723206116778L;

	public ViewMapping(AbstractMetadata metadata) {
		super(MappingTypeConstants.VIEW, metadata);
	}
}
