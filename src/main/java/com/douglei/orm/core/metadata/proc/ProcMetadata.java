package com.douglei.orm.core.metadata.proc;

import com.douglei.orm.core.metadata.CreateMode;
import com.douglei.orm.core.metadata.view.ViewMetadata;

/**
 * 
 * @author DougLei
 */
public class ProcMetadata extends ViewMetadata{

	public ProcMetadata(String name, String oldName, CreateMode createMode, String content) {
		super(name, oldName, createMode, content);
	}
}
