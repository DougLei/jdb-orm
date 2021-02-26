package com.douglei.orm.mapping.impl.procedure;

import java.io.InputStream;

import org.dom4j.Element;

import com.douglei.orm.configuration.Dom4jUtil;
import com.douglei.orm.mapping.MappingParser;
import com.douglei.orm.mapping.MappingSubject;
import com.douglei.orm.mapping.MappingTypeNameConstants;
import com.douglei.orm.mapping.impl.MappingParserContext;
import com.douglei.orm.mapping.impl.procedure.metadata.ProcedureMetadata;
import com.douglei.orm.mapping.impl.procedure.metadata.ProcedureMetadataParser;

/**
 * 
 * @author DougLei
 */
class ProcedureMappingParser extends MappingParser{
	private static ProcedureMetadataParser procedureMetadataParser = new ProcedureMetadataParser();

	@Override
	public MappingSubject parse(InputStream input) throws Exception {
		Element rootElement = MappingParserContext.getSAXReader().read(input).getRootElement();
		ProcedureMetadata procedureMetadata = (ProcedureMetadata) procedureMetadataParser.parse(Dom4jUtil.getElement(MappingTypeNameConstants.PROCEDURE, rootElement));
		return buildMappingSubjectByDom4j(new ProcedureMapping(procedureMetadata), rootElement);
	}
}
