package com.douglei.orm.mapping.impl.table;

import java.io.InputStream;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.type.MappingType;
import com.douglei.orm.mapping.type.MappingTypeNameConstants;

/**
 * 
 * @author DougLei
 */
public class TableMappingType extends MappingType{
	
	public TableMappingType() {
		super(MappingTypeNameConstants.TABLE, ".tmp.xml", 10, true, true);
	}

	@Override
	public Mapping parse(InputStream input) throws Exception {
		return new TableMappingParser().parse(input);
	}
}
