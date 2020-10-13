package com.douglei.orm.mapping.impl.procedure.parser;

import com.douglei.orm.mapping.impl.procedure.metadata.ProcedureMetadata;
import com.douglei.orm.mapping.impl.table.metadata.CreateMode;
import com.douglei.orm.mapping.impl.view.metadata.parser.ViewMetadataParser;
import com.douglei.orm.mapping.type.MappingTypeConstants;

/**
 * procedure元数据解析
 * @author DougLei
 */
public class ProcedureMetadataParser extends ViewMetadataParser {

	@Override
	protected String elementName() {
		return MappingTypeConstants.PROCEDURE;
	}

	@Override
	protected ProcedureMetadata newInstance(String name, String oldName, CreateMode createMode, String script) {
		return new ProcedureMetadata(name, oldName, createMode, script);
	}
}
