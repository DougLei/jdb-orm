package com.douglei.orm.mapping.metadata;

import com.douglei.orm.EnvironmentContext;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractMetadata implements Metadata{
	
	protected String name; // 名
	protected String oldName;// 旧名
	
	public AbstractMetadata() {}
	protected AbstractMetadata(String name, String oldName) {
		EnvironmentContext.getDialect().getObjectHandler().validateObjectName(name); // 验证name的长度是否超过数据库支持的最大长度
		
		this.name = name.toUpperCase();
		if(StringUtil.isEmpty(oldName)) 
			this.oldName = this.name;
		else 
			this.oldName = oldName.toUpperCase();
	}
	
	
	@Override
	public String getCode() {
		return name;
	}
	public final String getName() {
		return name;
	}
	public final String getOldName() {
		return oldName;
	}
	
	/**
	 * 是否修改了name, 即name与oldName不同
	 * @return
	 */
	public final boolean isUpdateName() {
		return oldName != null && name != oldName;
	}
}
