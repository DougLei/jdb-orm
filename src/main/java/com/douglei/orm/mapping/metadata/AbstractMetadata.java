package com.douglei.orm.mapping.metadata;

import com.douglei.orm.EnvironmentContext;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractMetadata implements Metadata{
	private static final long serialVersionUID = -7217850203830749920L;
	
	protected String name; // 名
	protected String oldName;// 旧名
	
	public AbstractMetadata() {}
	public AbstractMetadata(String name, String oldName) {
		// 设置name的同时, 对name进行验证
		EnvironmentContext.getDialect().getObjectHandler().validateObjectName(name);
		
		this.name = name.toUpperCase();
		if(StringUtil.isEmpty(oldName)) {
			this.oldName = this.name;
		}else {
			this.oldName = oldName.toUpperCase();
		}
	}
	
	public String getName() {
		return name;
	}
	public String getOldName() {
		return oldName;
	}
}
