package com.douglei.orm.mapping.impl.view.metadata;

import com.douglei.orm.mapping.metadata.AbstractMetadata;

/**
 * 
 * @author DougLei
 */
public class ViewMetadata extends AbstractMetadata {
	private static final long serialVersionUID = -1453945598551390420L;
	
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
