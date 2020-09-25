package com.douglei.orm.core.metadata.procedure;

import com.douglei.orm.core.metadata.view.ViewMetadata;

/**
 * 
 * @author DougLei
 */
public class ProcedureMetadata extends ViewMetadata{
	private static final long serialVersionUID = 406374837795440256L;

	public ProcedureMetadata(String name, String oldName, String script) {
		super(name, oldName, script);
	}
}
