package com.douglei.orm.mapping.impl.view;

import java.io.InputStream;

import com.douglei.orm.mapping.MappingSubject;
import com.douglei.orm.mapping.MappingType;
import com.douglei.orm.mapping.MappingTypeNameConstants;
import com.douglei.orm.mapping.handler.entity.AddOrCoverMappingEntity;

/**
 * 
 * @author DougLei
 */
public class ViewMappingType extends MappingType{
	
	public ViewMappingType() {
		super(MappingTypeNameConstants.VIEW, ".vmp.xml", 20, true);
	}

	@Override
	public MappingSubject parse(AddOrCoverMappingEntity entity, InputStream input) throws Exception {
		return new ViewMappingParser().parse(entity, input);
	}
}
