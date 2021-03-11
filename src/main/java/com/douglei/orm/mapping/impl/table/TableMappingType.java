package com.douglei.orm.mapping.impl.table;

import java.io.InputStream;

import com.douglei.orm.mapping.MappingSubject;
import com.douglei.orm.mapping.MappingType;
import com.douglei.orm.mapping.MappingTypeNameConstants;
import com.douglei.orm.mapping.handler.entity.AddOrCoverMappingEntity;

/**
 * 
 * @author DougLei
 */
public class TableMappingType extends MappingType{
	private static TableMappingParser parser = new TableMappingParser();
	
	public TableMappingType() {
		super(MappingTypeNameConstants.TABLE, ".tmp.xml", 10, true);
	}

	@Override
	public MappingSubject parse(AddOrCoverMappingEntity entity, InputStream input) throws Exception {
		return parser.parse(entity, input);
	}
}
