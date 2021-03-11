package com.douglei.orm.mapping.impl.sql;

import java.io.InputStream;

import com.douglei.orm.mapping.MappingSubject;
import com.douglei.orm.mapping.MappingType;
import com.douglei.orm.mapping.MappingTypeNameConstants;
import com.douglei.orm.mapping.handler.entity.AddOrCoverMappingEntity;
import com.douglei.orm.mapping.impl.sql.parser.SqlMappingParser;

/**
 * 
 * @author DougLei
 */
public class SqlMappingType extends MappingType{
	private static SqlMappingParser parser = new SqlMappingParser();
	
	public SqlMappingType() {
		super(MappingTypeNameConstants.SQL, ".smp.xml", 40, false);
	}

	@Override
	public MappingSubject parse(AddOrCoverMappingEntity entity, InputStream input) throws Exception {
		return parser.parse(entity, input);
	}
}
