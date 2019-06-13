package com.douglei.orm.core.dialect.datatype.handler;

/**
 * 数据库类型的验证结果对象
 * @author DougLei
 */
public class DataTypeValidateResult {
	private String shouldDataType;// 应该的数据类型
	private String actualDataType;// 实际的数据类型
	
	public DataTypeValidateResult(String shouldDataType, String actualDataType) {
		this.shouldDataType = shouldDataType;
		this.actualDataType = actualDataType;
	}
	
	public String getShouldDataType() {
		return shouldDataType;
	}
	public String getActualDataType() {
		return actualDataType;
	}
}
