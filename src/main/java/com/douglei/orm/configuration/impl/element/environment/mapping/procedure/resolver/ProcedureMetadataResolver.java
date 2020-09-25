package com.douglei.orm.configuration.impl.element.environment.mapping.procedure.resolver;

import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.impl.element.environment.mapping.view.resolver.ViewMetadataResolver;
import com.douglei.orm.core.metadata.procedure.ProcedureMetadata;

/**
 * procedure元数据解析
 * @author DougLei
 */
public class ProcedureMetadataResolver extends ViewMetadataResolver {

	@Override
	protected String elementName() {
		return MappingType.PROCEDURE.getName();
	}

	@Override
	protected ProcedureMetadata newInstance(String name, String oldName, String script) {
		return new ProcedureMetadata(name, oldName, script);
	}
}
