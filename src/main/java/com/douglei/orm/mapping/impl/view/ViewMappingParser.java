package com.douglei.orm.mapping.impl.view;

import java.io.InputStream;

import org.dom4j.Element;

import com.douglei.orm.configuration.Dom4jUtil;
import com.douglei.orm.mapping.MappingParseToolContext;
import com.douglei.orm.mapping.MappingParser;
import com.douglei.orm.mapping.MappingSubject;
import com.douglei.orm.mapping.MappingTypeNameConstants;
import com.douglei.orm.mapping.handler.entity.AddOrCoverMappingEntity;
import com.douglei.orm.mapping.impl.view.metadata.ViewMetadata;
import com.douglei.orm.mapping.impl.view.metadata.ViewMetadataParser;

/**
 * 
 * @author DougLei
 */
class ViewMappingParser extends MappingParser{
	private static ViewMetadataParser viewMetadataParser = new ViewMetadataParser();
	
	@Override
	public MappingSubject parse(AddOrCoverMappingEntity entity, InputStream input) throws Exception {
		Element rootElement = MappingParseToolContext.getMappingParseTool().getSAXReader().read(input).getRootElement();
		ViewMetadata viewMetadata = viewMetadataParser.parse(Dom4jUtil.getElement(MappingTypeNameConstants.VIEW, rootElement));
		return buildMappingSubjectByDom4j(new ViewMapping(viewMetadata), rootElement);
	}
}
