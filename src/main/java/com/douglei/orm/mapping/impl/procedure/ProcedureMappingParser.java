package com.douglei.orm.mapping.impl.procedure;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.Element;

import com.douglei.orm.configuration.Dom4jUtil;
import com.douglei.orm.mapping.MappingParser;
import com.douglei.orm.mapping.impl.MappingParserContext;
import com.douglei.orm.mapping.impl.procedure.metadata.ProcedureMetadata;
import com.douglei.orm.mapping.impl.procedure.parser.ProcedureMetadataParser;
import com.douglei.orm.metadata.type.MetadataTypeNameConstants;

/**
 * 
 * @author DougLei
 */
class ProcedureMappingParser extends MappingParser<ProcedureMapping>{
	private static ProcedureMetadataParser procedureMetadataParser = new ProcedureMetadataParser();

	@Override
	public ProcedureMapping parse(InputStream input) throws Exception {
		Document document = MappingParserContext.getSAXReader().read(input);
		Element rootElement = document.getRootElement();
		
		Element procedureElement = Dom4jUtil.getElement(MetadataTypeNameConstants.PROCEDURE, rootElement);
		ProcedureMetadata procedureMetadata = (ProcedureMetadata) procedureMetadataParser.parse(procedureElement);
		return new ProcedureMapping(procedureMetadata, getMappingPropertyByDom4j(rootElement, procedureMetadata.getCode(), MetadataTypeNameConstants.PROCEDURE));
	}
}
