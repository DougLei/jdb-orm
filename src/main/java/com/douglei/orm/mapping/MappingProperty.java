package com.douglei.orm.mapping;

import java.io.Serializable;

import com.douglei.tools.datatype.DataTypeValidateUtil;

/**
 * 
 * @author DougLei
 */
public class MappingProperty implements Serializable{
	private String code; // mapping的唯一编码, 与对应mapping的编码一样
	private String type; // mapping的类型
	
	private int order; // mapping的排序值
	private boolean supportCover=true; // mapping是否支持被覆盖, 默认为true
	private boolean supportDelete=true; // mapping是否支持被删除, 默认为true
	private String extend; // mapping的扩展属性, 可由第三方扩展
	
	public MappingProperty(String code, String type) {
		this.code = code;
		this.type = type;
	}
	
	/**
	 * 设置属性值
	 * @param order mapping的排序值, 默认0
	 * @param supportCover mapping是否支持被覆盖
	 * @param supportDelete mapping是否支持被删除
	 * @param extend mapping的扩展属性, 可由第三方扩展
	 */
	void setValues(String order, String supportCover, String supportDelete, String extend) {
		if(DataTypeValidateUtil.isInteger(order))
			this.order = Integer.parseInt(order);
		
		this.supportCover = !"false".equalsIgnoreCase(supportCover);
		this.supportDelete = !"false".equalsIgnoreCase(supportDelete);
		this.extend = extend;
	}
	
	
	public String getCode() {
		return code;
	}
	public String getType() {
		return type;
	}
	public int getOrder() {
		return order;
	}
	public boolean supportCover() {
		return supportCover;
	}
	public boolean supportDelete() {
		return supportDelete;
	}
	public String getExtend() {
		return extend;
	}
	
	@Override
	public String toString() {
		return "MappingProperty [code=" + code + ", type=" + type + ", order=" + order + ", supportCover=" + supportCover + ", supportDelete=" + supportDelete + ", extend=" + extend + "]";
	}
}
