package com.douglei.orm.mapping.impl.sql;

import java.io.InputStream;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.type.MappingType;
import com.douglei.orm.mapping.type.MappingTypeNameConstants;

/**
 * 
 * @author DougLei
 */
public class SqlMappingType extends MappingType{
	
	public SqlMappingType() {
		super(MappingTypeNameConstants.SQL, ".smp.xml", 40, false, true);
	}

	@Override
	public Mapping parse(InputStream input) throws Exception {
		return new SqlMappingParser().parse(input);
	}
}
