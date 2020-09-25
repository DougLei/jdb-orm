package com.douglei.orm.core.metadata.procedure;

import com.douglei.orm.core.metadata.view.ViewMetadata;

/**
 * 
 * @author DougLei
 */
public class ProcedureMetadata extends ViewMetadata{
	private static final long serialVersionUID = -4411237637382749351L;
	
	public ProcedureMetadata() {}
	public ProcedureMetadata(String name, String oldName, String content) {
		super(name, oldName, content);
	}
}
