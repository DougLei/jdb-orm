package com.douglei.orm.sessionfactory.sessions.session.sql.impl.execute;

/**
 * SqlExecuteHandler 的用途实体
 * @author DougLei
 */
public abstract class PurposeEntity {
	private boolean getSqlParameters;
	private boolean getSqlParameterValues;
	
	/**
	 * 
	 * @param getSqlParameters 获取sql参数集合
	 * @param getSqlParameterValues 获取sql参数值集合, 用在执行sql语句时, 通过PreparedStatement.setValue
	 */
	public PurposeEntity(boolean getSqlParameters, boolean getSqlParameterValues) {
		this.getSqlParameters = getSqlParameters;
		this.getSqlParameterValues = getSqlParameterValues;
	}

	public boolean isGetSqlParameters() {
		return getSqlParameters;
	}
	public boolean isGetSqlParameterValues() {
		return getSqlParameterValues;
	}

	/**
	 * 获取用途枚举实例
	 * @return
	 */
	public abstract Purpose getPurpose();
}
