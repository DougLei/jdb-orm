package com.douglei.orm.mapping.impl.sql;

import java.io.InputStream;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.metadata.type.MetadataType;
import com.douglei.orm.metadata.type.MetadataTypeNameConstants;

/**
 * 
 * @author DougLei
 */
public class SqlMappingType extends MetadataType{
	
	public SqlMappingType() {
		super(MetadataTypeNameConstants.SQL, ".smp.xml", 40, false);
	}

	@Override
	public Mapping parse(InputStream input) throws Exception {
		return new SqlMappingParser().parse(input);
	}
}
