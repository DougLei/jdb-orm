package com.douglei.orm.mapping.impl.view.metadata;

import com.douglei.orm.mapping.impl.table.metadata.CreateMode;
import com.douglei.orm.mapping.metadata.AbstractDatabaseObjectMetadata;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class ViewMetadata extends AbstractDatabaseObjectMetadata {
	private static final long serialVersionUID = -4631261045702701161L;
	
	private CreateMode createMode; // 创建模式
	private String script; // 脚本内容
	
	public ViewMetadata(String name, String oldName, CreateMode createMode, String script) {
		super(name);
		super.name = name;
		super.oldName = StringUtil.isEmpty(oldName)?name:oldName;
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
