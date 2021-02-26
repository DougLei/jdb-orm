package com.douglei.orm.mapping.metadata;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractMetadata implements Metadata{
	protected String name; // 名
	protected String oldName;// 旧名
	
	/**
	 * 获取元数据的唯一编码
	 * @return
	 */
	public String getCode() {
		return name;
	}
	
	/**
	 * 获取元数据的名称
	 * @return
	 */
	public final String getName() {
		return name;
	}
	
	/**
	 * 获取元数据的旧名称
	 * @return
	 */
	public final String getOldName() {
		return oldName;
	}
	
	/**
	 * 是否修改了名称; 即name与oldName不同
	 * @return
	 */
	public final boolean isUpdateName() {
		return oldName != null && !name.equals(oldName);
	}
}
