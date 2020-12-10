package com.douglei.orm.mapping.metadata;

import com.douglei.orm.configuration.EnvironmentContext;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractMetadata implements Metadata{
	private static final long serialVersionUID = 7166906399729424369L;
	
	protected String name; // 名
	protected String oldName;// 旧名
	
	public AbstractMetadata() {}
	protected AbstractMetadata(String name) {
		EnvironmentContext.getDialect().getObjectHandler().validateObjectName(name); // 验证name的长度是否超过数据库支持的最大长度
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
