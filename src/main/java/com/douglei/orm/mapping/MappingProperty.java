package com.douglei.orm.mapping;

import java.io.Serializable;

import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 
 * @author DougLei
 */
public class MappingProperty implements Serializable{
	
	private String code; // mapping的唯一标识, 与对应mapping的code值完全一样
	private String type; // mapping的类型, 值来自 {@link MappingTypeConstants} 中的映射类型名, 或自定义且完成注册的映射类型名
	private boolean supportCover=true; // mapping是否支持被覆盖
	private boolean supportDelete=true; // mapping是否支持被删除
	private String extendExpr; // mapping的扩展属性, 可由第三方扩展
	
	public MappingProperty(String code, String type) {
		this.code = code;
		this.type = type;
	}
	
	/**
	 * 设置值
	 * @param supportCover mapping是否支持被覆盖, 默认true
	 * @param supportDelete mapping是否支持被删除, 默认true
	 * @param extendExpr mapping的扩展属性, 可由第三方扩展
	 */
	public void setValues(String supportCover, String supportDelete, String extendExpr) {
		if(VerifyTypeMatchUtil.isBoolean(supportCover))
			this.supportCover = Boolean.parseBoolean(supportCover);
		
		if(VerifyTypeMatchUtil.isBoolean(supportDelete))
			this.supportDelete = Boolean.parseBoolean(supportDelete);
		
		if(StringUtil.notEmpty(extendExpr))
			this.extendExpr = extendExpr;
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
	public String getExtendExpr() {
		return extendExpr;
	}
	
	@Override
	public String toString() {
		return "MappingProperty [code=" + code + ", type=" + type + ", supportCover=" + supportCover + ", supportDelete=" + supportDelete + ", extendExpr=" + extendExpr + "]";
	}
}
