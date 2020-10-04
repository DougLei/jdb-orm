package com.douglei.orm.mapping.impl.procedure;

import java.io.InputStream;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.type.MappingType;
import com.douglei.orm.mapping.type.MappingTypeNameConstants;

/**
 * 
 * @author DougLei
 */
public class ProcedureMappingType extends MappingType{
	
	public ProcedureMappingType() {
		super(MappingTypeNameConstants.PROCEDURE, ".pmp.xml", 30, true, false);
	}

	@Override
	public Mapping parse(InputStream input) throws Exception{
		return new ProcedureMappingParser().parse(input);
	}
}