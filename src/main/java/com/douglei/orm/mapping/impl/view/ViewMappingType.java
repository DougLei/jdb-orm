package com.douglei.orm.mapping.impl.view;

import java.io.InputStream;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.metadata.type.MetadataType;
import com.douglei.orm.metadata.type.MetadataTypeNameConstants;

/**
 * 
 * @author DougLei
 */
public class ViewMappingType extends MetadataType{
	
	public ViewMappingType() {
		super(MetadataTypeNameConstants.VIEW, ".vmp.xml", 20, true);
	}

	@Override
	public Mapping parse(InputStream input) throws Exception {
		return new ViewMappingParser().parse(input);
	}
}
