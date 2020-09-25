package com.douglei.orm.core.metadata.view;

import com.douglei.orm.core.metadata.AbstractMetadata;
import com.douglei.orm.core.metadata.Metadata;

/**
 * 
 * @author DougLei
 */
public class ViewMetadata extends AbstractMetadata implements Metadata{
	private static final long serialVersionUID = -5934867049115808322L;
	
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
