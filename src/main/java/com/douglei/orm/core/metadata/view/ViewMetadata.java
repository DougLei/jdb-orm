package com.douglei.orm.core.metadata.view;

import com.douglei.orm.core.metadata.AbstractMetadata;

/**
 * 
 * @author DougLei
 */
public class ViewMetadata extends AbstractMetadata {
	private static final long serialVersionUID = -4229191306971230892L;
	
	private String script; // 脚本内容
	
	public ViewMetadata(String name, String oldName, String script) {
		super(name, oldName);
		this.script = script;
	}
	
	@Override
	public String getCode() {
		return name;	}

	public String getScript() {
		return script;
	}
}
