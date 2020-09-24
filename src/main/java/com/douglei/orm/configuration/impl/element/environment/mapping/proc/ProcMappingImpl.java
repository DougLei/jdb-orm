package com.douglei.orm.configuration.impl.element.environment.mapping.proc;

import org.dom4j.Element;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.impl.element.environment.mapping.proc.resolver.ProcMetadataResolver;
import com.douglei.orm.configuration.impl.util.Dom4jUtil;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.proc.ProcMetadata;

/**
 * proc 映射
 * @author DougLei
 */
public class ProcMappingImpl implements Mapping {
	private static final ProcMetadataResolver procMetadataResolver = new ProcMetadataResolver();
	
	private ProcMetadata procMetadata;
	
	public ProcMappingImpl(Element rootElement) {
		Element procElement = Dom4jUtil.validateElementExists(getMappingType().getName(), rootElement);
		procMetadata = (ProcMetadata) procMetadataResolver.resolving(procElement);
	}

	@Override
	public String getCode() {
		return procMetadata.getCode();
	}

	@Override
	public MappingType getMappingType() {
		return MappingType.PROC;
	}

	@Override
	public Metadata getMetadata() {
		return procMetadata;
	}
	
	
}
