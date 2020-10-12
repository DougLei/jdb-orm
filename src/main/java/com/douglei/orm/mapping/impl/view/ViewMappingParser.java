package com.douglei.orm.mapping.impl.view;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.Element;

import com.douglei.orm.mapping.impl.MappingParserContext;
import com.douglei.orm.mapping.impl.view.metadata.ViewMetadata;
import com.douglei.orm.mapping.impl.view.metadata.parser.ViewMetadataParser;
import com.douglei.orm.mapping.type.MappingTypeConstants;
import com.douglei.orm.util.Dom4jUtil;

/**
 * 
 * @author DougLei
 */
class ViewMappingParser {
	private static ViewMetadataParser viewMetadataParser = new ViewMetadataParser();
	
	/**
	 * 解析
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public ViewMapping parse(InputStream input) throws Exception {
		Document document = MappingParserContext.getSAXReader().read(input);
		Element viewElement = Dom4jUtil.getElement(MappingTypeConstants.VIEW, document.getRootElement());
		
		ViewMetadata viewMetadata = viewMetadataParser.parse(viewElement);
		return new ViewMapping(viewMetadata);
	}
}
