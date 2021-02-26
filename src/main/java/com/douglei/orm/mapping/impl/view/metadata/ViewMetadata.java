package com.douglei.orm.mapping.impl.view.metadata;

import com.douglei.orm.configuration.environment.CreateMode;
import com.douglei.orm.mapping.metadata.AbstractMetadata;

/**
 * 
 * @author DougLei
 */
public class ViewMetadata extends AbstractMetadata {
	private CreateMode createMode; // 创建模式
	private String script; // 脚本内容
	
	public ViewMetadata(String name, String oldName, CreateMode createMode, String script) {
		this.name = name;
		this.oldName = oldName;
		
		this.createMode = createMode;
		this.script = script;
	}
	
	public CreateMode getCreateMode() {
		return createMode;
	}
	public String getScript() {
		return script;
	}
}
