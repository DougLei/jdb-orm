package com.douglei.orm.mapping.metadata;

import com.douglei.orm.metadata.Metadata;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractMetadata implements Metadata{
	protected String name; // 名
	protected String oldName;// 旧名
	
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
		return oldName != null && !name.equals(oldName);
	}
}
