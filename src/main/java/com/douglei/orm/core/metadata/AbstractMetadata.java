package com.douglei.orm.core.metadata;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractMetadata {
	protected String name; // 名
	protected String oldName;// 旧名
	protected CreateMode createMode;// 创建模式
	
	public AbstractMetadata(String name, String oldName, CreateMode createMode) {
		// 设置name的同时, 对name进行验证
		EnvironmentContext.getDialect().getDBObjectHandler().validateDBObjectName(name);
		
		this.name = name.toUpperCase();
		if(StringUtil.isEmpty(oldName)) {
			this.oldName = this.name;
		}else {
			this.oldName = oldName.toUpperCase();
		}
		
		this.createMode = createMode;
	}
	
	public String getName() {
		return name;
	}
	public String getOldName() {
		return oldName;
	}
	public CreateMode getCreateMode() {
		return createMode;
	}
}
