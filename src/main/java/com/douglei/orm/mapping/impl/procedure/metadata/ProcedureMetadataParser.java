package com.douglei.orm.mapping.impl.procedure.metadata;

import com.douglei.orm.configuration.environment.CreateMode;
import com.douglei.orm.mapping.impl.view.metadata.ViewMetadataParser;

/**
 * procedure元数据解析
 * @author DougLei
 */
public class ProcedureMetadataParser extends ViewMetadataParser {

	@Override
	protected ProcedureMetadata newInstance(String name, String oldName, CreateMode createMode, String script) {
		return new ProcedureMetadata(name, oldName, createMode, script);
	}
}
