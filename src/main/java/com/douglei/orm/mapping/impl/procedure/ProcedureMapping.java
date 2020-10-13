package com.douglei.orm.mapping.impl.procedure;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.metadata.AbstractMetadata;
import com.douglei.orm.mapping.type.MappingTypeConstants;

/**
 * procedure 映射
 * @author DougLei
 */
public class ProcedureMapping extends Mapping {

	public ProcedureMapping(AbstractMetadata metadata) {
		super(MappingTypeConstants.PROCEDURE, metadata);
	}
}
