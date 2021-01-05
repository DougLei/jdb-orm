package com.douglei.orm.sessionfactory.sessions.session.sql;

/**
 * sql的用途实体
 * @author DougLei
 */
public abstract class PurposeEntity {
	private boolean isGetParameters; 
	private boolean isGetParameterValues;  
	
	protected PurposeEntity(boolean isGetParameters, boolean isGetParameterValues) {
		this.isGetParameters = isGetParameters;
		this.isGetParameterValues = isGetParameterValues;
	}

	/**
	 * 是否要获取sql参数集合
	 * @return
	 */
	public final boolean isGetParameters() {
		return isGetParameters;
	}
	/**
	 * 是否要获取sql参数值集合
	 * @return
	 */
	public final boolean isGetParameterValues() {
		return isGetParameterValues;
	}

	/**
	 * 获取用途枚举实例
	 * @return
	 */
	public abstract Purpose getPurpose();
}
