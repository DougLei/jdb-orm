package com.douglei.orm.mapping.impl.sql;

import java.io.InputStream;

import com.douglei.orm.mapping.MappingSubject;
import com.douglei.orm.mapping.MappingType;
import com.douglei.orm.mapping.MappingTypeNameConstants;

/**
 * 
 * @author DougLei
 */
public class SqlMappingType extends MappingType{
	
	public SqlMappingType() {
		super(MappingTypeNameConstants.SQL, ".smp.xml", 40, false);
	}

	@Override
	public MappingSubject parse(InputStream input) throws Exception {
		return new SqlMappingParser().parse(input);
	}
}
