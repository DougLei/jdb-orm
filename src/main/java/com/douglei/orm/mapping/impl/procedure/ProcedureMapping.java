package com.douglei.orm.mapping.impl.procedure;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.metadata.Metadata;
import com.douglei.orm.mapping.type.MappingTypeConstants;

/**
 * procedure 映射
 * @author DougLei
 */
public class ProcedureMapping extends Mapping {
	private static final long serialVersionUID = 1519821354288730820L;

	public ProcedureMapping(Metadata metadata) {
		super(MappingTypeConstants.PROCEDURE, metadata);
	}
}
