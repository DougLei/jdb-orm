package com.douglei.orm.mapping.impl.procedure;

import java.io.InputStream;

import com.douglei.orm.mapping.MappingSubject;
import com.douglei.orm.mapping.MappingType;
import com.douglei.orm.mapping.MappingTypeNameConstants;

/**
 * 
 * @author DougLei
 */
public class ProcedureMappingType extends MappingType{
	
	public ProcedureMappingType() {
		super(MappingTypeNameConstants.PROCEDURE, ".pmp.xml", 30, true);
	}

	@Override
	public MappingSubject parse(InputStream input) throws Exception{
		return new ProcedureMappingParser().parse(input);
	}
}