package com.douglei.orm.mapping.impl.table;

import java.io.InputStream;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.metadata.type.MetadataType;
import com.douglei.orm.metadata.type.MetadataTypeNameConstants;

/**
 * 
 * @author DougLei
 */
public class TableMappingType extends MetadataType{
	
	public TableMappingType() {
		super(MetadataTypeNameConstants.TABLE, ".tmp.xml", 10, true);
	}

	@Override
	public Mapping parse(InputStream input) throws Exception {
		return new TableMappingParser().parse(input);
	}
}
