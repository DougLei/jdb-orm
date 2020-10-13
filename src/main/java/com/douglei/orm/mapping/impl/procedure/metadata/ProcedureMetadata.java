package com.douglei.orm.mapping.impl.procedure.metadata;

import com.douglei.orm.mapping.impl.table.metadata.CreateMode;
import com.douglei.orm.mapping.impl.view.metadata.ViewMetadata;

/**
 * 
 * @author DougLei
 */
public class ProcedureMetadata extends ViewMetadata{
	private static final long serialVersionUID = 4712998655620762233L;

	public ProcedureMetadata(String name, String oldName, CreateMode createMode, String script) {
		super(name, oldName, createMode, script);
	}
}
