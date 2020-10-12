package com.douglei.orm.mapping;

/**
 * 
 * @author DougLei
 */
public class MappingFeature {
	private boolean allowCover=true; // mapping是否可以被覆盖
	private boolean allowDelete=true; // mapping是否可以被删除
	private Object extend; // mapping的扩展特性, 可由第三方扩展

	public void setAllowCover(boolean allowCover) {
		this.allowCover = allowCover;
	}
	public void setAllowDelete(boolean allowDelete) {
		this.allowDelete = allowDelete;
	}
	public void setExtend(Object extend) {
		this.extend = extend;
	}
	
	public boolean isAllowCover() {
		return allowCover;
	}
	public boolean isAllowDelete() {
		return allowDelete;
	}
	public Object getExtend() {
		return extend;
	}
	
	@Override
	public String toString() {
		return "MappingFeature [allowCover=" + allowCover + ", allowDelete=" + allowDelete + ", extend=" + extend + "]";
	}
}
