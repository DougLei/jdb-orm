package com.douglei.orm.configuration.impl.element.environment.mapping.view;

import org.dom4j.Element;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.impl.element.environment.mapping.view.resolver.ViewMetadataResolver;
import com.douglei.orm.configuration.impl.util.Dom4jUtil;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.view.ViewMetadata;

/**
 * view 映射
 * @author DougLei
 */
public class ViewMappingImpl implements Mapping {
	private static final ViewMetadataResolver viewMetadataResolver = new ViewMetadataResolver();
	
	private ViewMetadata viewMetadata;
	
	public ViewMappingImpl(Element rootElement) {
		Element viewElement = Dom4jUtil.validateElementExists(getMappingType().getName(), rootElement);
		viewMetadata = viewMetadataResolver.resolving(viewElement);
	}
	
	@Override
	public String getCode() {
		return viewMetadata.getCode();
	}
	
	@Override
	public MappingType getMappingType() {
		return MappingType.VIEW;
	}

	@Override
	public Metadata getMetadata() {
		return viewMetadata;
	}
}
