package com.douglei.orm.mapping.impl.procedure;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.Element;

import com.douglei.orm.mapping.impl.MappingParserContext;
import com.douglei.orm.mapping.impl.procedure.metadata.ProcedureMetadata;
import com.douglei.orm.mapping.impl.procedure.resolver.ProcedureMetadataResolver;
import com.douglei.orm.mapping.type.MappingTypeConstants;
import com.douglei.orm.util.Dom4jUtil;

/**
 * 
 * @author DougLei
 */
class ProcedureMappingParser {
	private static ProcedureMetadataResolver procedureMetadataParser = new ProcedureMetadataResolver();

	/**
	 * 解析
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public ProcedureMapping parse(InputStream input) throws Exception {
		Document document = MappingParserContext.getSAXReader().read(input);
		Element procedureElement = Dom4jUtil.getElement(MappingTypeConstants.PROCEDURE, document.getRootElement());
		
		ProcedureMetadata procedureMetadata = (ProcedureMetadata) procedureMetadataParser.parse(procedureElement);
		return new ProcedureMapping(procedureMetadata);
	}
}
