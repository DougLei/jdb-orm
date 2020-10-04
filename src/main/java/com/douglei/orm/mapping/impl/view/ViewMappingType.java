package com.douglei.orm.mapping.impl.view;

import java.io.InputStream;

import com.douglei.orm.mapping.Mapping;
import com.douglei.orm.mapping.type.MappingType;
import com.douglei.orm.mapping.type.MappingTypeNameConstants;

/**
 * 
 * @author DougLei
 */
public class ViewMappingType extends MappingType{
	
	public ViewMappingType() {
		super(MappingTypeNameConstants.VIEW, ".vmp.xml", 20, true, false);
	}

	@Override
	public Mapping parse(InputStream input) throws Exception {
		return new ViewMappingParser().parse(input);
	}
}
