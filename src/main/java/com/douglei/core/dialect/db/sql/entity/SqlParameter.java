package com.douglei.core.dialect.db.sql.entity;

import com.douglei.core.metadata.sql.SqlParameterMetadata;

/**
 * 
 * @author DougLei
 */
public class SqlParameter extends SqlParameterMetadata{
	
	protected short length;// 长度
	protected short precision;// 精度
	protected boolean nullabled;// 是否可为空
	protected String defaultValue;// 默认值
	
	public SqlParameter(String parameterConfigurationText) {
		super(parameterConfigurationText, false);
	}
}
