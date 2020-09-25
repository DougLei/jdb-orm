package com.douglei.orm.configuration.impl.element.environment.mapping.procedure;

import org.dom4j.Element;

import com.douglei.orm.configuration.environment.mapping.Mapping;
import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.impl.element.environment.mapping.procedure.resolver.ProcedureMetadataResolver;
import com.douglei.orm.configuration.impl.util.Dom4jUtil;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.procedure.ProcedureMetadata;

/**
 * proc 映射
 * @author DougLei
 */
public class ProcedureMappingImpl implements Mapping {
	private static final long serialVersionUID = -4181309896969977116L;
	private static final ProcedureMetadataResolver procedureMetadataResolver = new ProcedureMetadataResolver();
	
	private ProcedureMetadata procedureMetadata;
	
	public ProcedureMappingImpl(Element rootElement) {
		Element procElement = Dom4jUtil.validateElementExists(getMappingType().getName(), rootElement);
		procedureMetadata = (ProcedureMetadata) procedureMetadataResolver.resolving(procElement);
	}

	@Override
	public String getCode() {
		return procedureMetadata.getCode();
	}

	@Override
	public MappingType getMappingType() {
		return MappingType.PROCEDURE;
	}

	@Override
	public Metadata getMetadata() {
		return procedureMetadata;
	}
	
	
}
