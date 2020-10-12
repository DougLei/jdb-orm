package com.douglei.orm.mapping.impl.view.metadata;

import com.douglei.orm.mapping.metadata.AbstractMetadata;

/**
 * 
 * @author DougLei
 */
public class ViewMetadata extends AbstractMetadata {
	
	private String script; // 脚本内容
	
	public ViewMetadata(String name, String oldName, String script) {
		super(name, oldName);
		this.script = script;
	}
	
	public String getScript() {
		return script;
	}
}
