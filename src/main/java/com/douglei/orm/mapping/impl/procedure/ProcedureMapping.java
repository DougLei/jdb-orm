package com.douglei.orm.mapping.impl.procedure;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.MappingProperty;
import com.douglei.orm.mapping.metadata.AbstractMetadata;
import com.douglei.orm.metadata.type.MetadataTypeNameConstants;

/**
 * procedure 映射
 * @author DougLei
 */
public class ProcedureMapping extends Mapping {
	private static final long serialVersionUID = 5699739230288370355L;

	public ProcedureMapping(AbstractMetadata metadata, MappingProperty property) {
		super(MetadataTypeNameConstants.PROCEDURE, metadata, property);
	}
}
