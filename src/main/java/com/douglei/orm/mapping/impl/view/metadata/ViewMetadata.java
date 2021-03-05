package com.douglei.orm.mapping.impl.view.metadata;

import com.douglei.orm.configuration.environment.CreateMode;
import com.douglei.orm.mapping.metadata.AbstractDBObjectMetadata;

/**
 * 
 * @author DougLei
 */
public class ViewMetadata extends AbstractDBObjectMetadata {
	private String script; // 脚本内容
	
	public ViewMetadata(String name, String oldName, CreateMode createMode, String script) {
		this.name = name;
		this.oldName = oldName;
		
		this.createMode = createMode;
		this.script = script;
	}
	
	public String getScript() {
		return script;
	}
}
