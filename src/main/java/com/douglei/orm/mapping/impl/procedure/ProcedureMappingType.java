package com.douglei.orm.mapping.impl.procedure;

import java.io.InputStream;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.metadata.type.MetadataType;
import com.douglei.orm.metadata.type.MetadataTypeNameConstants;

/**
 * 
 * @author DougLei
 */
public class ProcedureMappingType extends MetadataType{
	
	public ProcedureMappingType() {
		super(MetadataTypeNameConstants.PROCEDURE, ".pmp.xml", 30, true);
	}

	@Override
	public Mapping parse(InputStream input) throws Exception{
		return new ProcedureMappingParser().parse(input);
	}
}