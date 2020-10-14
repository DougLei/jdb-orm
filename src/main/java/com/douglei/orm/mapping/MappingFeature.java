package com.douglei.orm.mapping;

import java.io.Serializable;

/**
 * 
 * @author DougLei
 */
public class MappingFeature implements Serializable{
	private static final long serialVersionUID = 308939161739639110L;
	
	private String code; // mapping的唯一标识, 与对应mapping的code值完全一样
	private String type; // mapping的类型, 值来自 {@link MappingTypeConstants} 中的映射类型名, 或自定义且完成注册的映射类型名
	private boolean supportCover=true; // mapping是否支持被覆盖
	private boolean supportDelete=true; // mapping是否支持被删除
	private Object extend; // mapping的扩展特性, 可由第三方扩展, 特别注意的是, 扩展的特性, 必须实现 java.io.Serializable 接口
	
	public void setCode(String code) {
		this.code = code;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setSupportCover(boolean supportCover) {
		this.supportCover = supportCover;
	}
	public void setSupportDelete(boolean supportDelete) {
		this.supportDelete = supportDelete;
	}
	public void setExtend(Object extend) {
		this.extend = extend;
	}
	
	public String getCode() {
		return code;
	}
	public String getType() {
		return type;
	}
	public boolean supportCover() {
		return supportCover;
	}
	public boolean supportDelete() {
		return supportDelete;
	}
	public Object getExtend() {
		return extend;
	}
	
	@Override
	public String toString() {
		return "MappingFeature [code=" + code + ", type=" + type + ", supportCover=" + supportCover + ", supportDelete=" + supportDelete + ", extend=" + extend + "]";
	}
}
