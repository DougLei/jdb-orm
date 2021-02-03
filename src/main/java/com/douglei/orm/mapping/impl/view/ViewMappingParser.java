package com.douglei.orm.mapping.impl.view;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.Element;

import com.douglei.orm.configuration.Dom4jUtil;
import com.douglei.orm.mapping.MappingParser;
import com.douglei.orm.mapping.impl.MappingParserContext;
import com.douglei.orm.mapping.impl.view.metadata.ViewMetadata;
import com.douglei.orm.mapping.impl.view.metadata.parser.ViewMetadataParser;
import com.douglei.orm.metadata.type.MetadataTypeNameConstants;

/**
 * 
 * @author DougLei
 */
class ViewMappingParser extends MappingParser<ViewMapping>{
	private static ViewMetadataParser viewMetadataParser = new ViewMetadataParser();
	
	@Override
	public ViewMapping parse(InputStream input) throws Exception {
		Document document = MappingParserContext.getSAXReader().read(input);
		Element rootElement = document.getRootElement();
		
		Element viewElement = Dom4jUtil.getElement(MetadataTypeNameConstants.VIEW, rootElement);
		ViewMetadata viewMetadata = viewMetadataParser.parse(viewElement);
		return new ViewMapping(viewMetadata, getMappingPropertyByDom4j(rootElement, viewMetadata.getCode(), MetadataTypeNameConstants.VIEW));
	}
}
