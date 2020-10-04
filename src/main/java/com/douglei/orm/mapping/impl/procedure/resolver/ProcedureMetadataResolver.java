package com.douglei.orm.mapping.impl.procedure.resolver;

import com.douglei.orm.mapping.impl.procedure.metadata.ProcedureMetadata;
import com.douglei.orm.mapping.impl.view.metadata.parser.ViewMetadataParser;
import com.douglei.orm.mapping.type.MappingTypeNameConstants;

/**
 * procedure元数据解析
 * @author DougLei
 */
public class ProcedureMetadataResolver extends ViewMetadataParser {

	@Override
	protected String elementName() {
		return MappingTypeNameConstants.PROCEDURE;
	}

	@Override
	protected ProcedureMetadata newInstance(String name, String oldName, String script) {
		return new ProcedureMetadata(name, oldName, script);
	}
}
