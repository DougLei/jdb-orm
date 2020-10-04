package com.douglei.orm.mapping.impl.procedure;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.metadata.Metadata;
import com.douglei.orm.mapping.type.MappingTypeNameConstants;

/**
 * procedure 映射
 * @author DougLei
 */
public class ProcedureMapping extends Mapping {

	public ProcedureMapping(Metadata metadata) {
		super(MappingTypeNameConstants.PROCEDURE, metadata);
	}
}
