package com.douglei.orm.mapping.impl.procedure.metadata;

import com.douglei.orm.mapping.impl.view.metadata.ViewMetadata;

/**
 * 
 * @author DougLei
 */
public class ProcedureMetadata extends ViewMetadata{
	private static final long serialVersionUID = 7421585311129830514L;

	public ProcedureMetadata(String name, String oldName, String script) {
		super(name, oldName, script);
	}
}
