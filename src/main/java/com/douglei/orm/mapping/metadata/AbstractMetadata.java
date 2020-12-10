package com.douglei.orm.mapping.metadata;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractMetadata implements Metadata{
	private static final long serialVersionUID = -2348381079483167958L;
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
		return oldName != null && name != oldName;
	}
}
