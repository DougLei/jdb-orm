package com.douglei.orm.configuration.impl.element.environment.mapping.proc.resolver;

import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.impl.element.environment.mapping.view.resolver.ViewMetadataResolver;
import com.douglei.orm.core.metadata.CreateMode;
import com.douglei.orm.core.metadata.proc.ProcMetadata;

/**
 * proc元数据解析
 * @author DougLei
 */
public class ProcMetadataResolver extends ViewMetadataResolver {

	@Override
	protected String elementName() {
		return MappingType.PROC.getName();
	}

	@Override
	protected ProcMetadata newInstance(String name, String oldName, CreateMode createMode, String content) {
		return new ProcMetadata(name, oldName, createMode, content);
	}
}
