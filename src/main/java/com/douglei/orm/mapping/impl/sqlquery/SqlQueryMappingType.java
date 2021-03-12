package com.douglei.orm.mapping.impl.sqlquery;

import java.io.InputStream;

import com.douglei.orm.mapping.MappingSubject;
import com.douglei.orm.mapping.MappingType;
import com.douglei.orm.mapping.MappingTypeNameConstants;
import com.douglei.orm.mapping.handler.entity.AddOrCoverMappingEntity;

/**
 * 
 * @author DougLei
 */
public class SqlQueryMappingType extends MappingType {
	private static SqlQueryMappingParser parser = new SqlQueryMappingParser();
	
	public SqlQueryMappingType() {
		super(MappingTypeNameConstants.SQL_QUERY, ".sqmp.xml", 50, false);
	}

	@Override
	public MappingSubject parse(AddOrCoverMappingEntity entity, InputStream input) throws Exception {
		return parser.parse(entity, input);
	}
}
