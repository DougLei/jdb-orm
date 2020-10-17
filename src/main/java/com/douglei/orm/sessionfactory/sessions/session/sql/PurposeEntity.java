package com.douglei.orm.sessionfactory.sessions.session.sql;

/**
 * sql的用途实体
 * @author DougLei
 */
public abstract class PurposeEntity {
	private boolean isGetSqlParameterValues;
	private boolean isGetSqlParameters;
	
	/**
	 * 
	 * @param isGetSqlParameterValues 是否要获取sql参数值集合
	 * @param isGetSqlParameters 是否要获取sql参数集合
	 */
	protected PurposeEntity(boolean isGetSqlParameterValues, boolean isGetSqlParameters) {
		this.isGetSqlParameterValues = isGetSqlParameterValues;
		this.isGetSqlParameters = isGetSqlParameters;
	}

	public final boolean isGetSqlParameterValues() {
		return isGetSqlParameterValues;
	}
	public final boolean isGetSqlParameters() {
		return isGetSqlParameters;
	}

	/**
	 * 获取用途枚举实例
	 * @return
	 */
	public abstract Purpose getPurpose();
}
