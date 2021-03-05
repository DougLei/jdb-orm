package com.douglei.orm.sql.statement;

/**
 * 执行insert语句返回的结果
 * @author DougLei
 */
public class InsertResult {
	private int row; // 影响的行数 
	private int autoIncrementIDValue; // 自增的主键值
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getAutoIncrementIDValue() {
		return autoIncrementIDValue;
	}
	public void setAutoIncrementIDValue(int autoIncrementIDValue) {
		this.autoIncrementIDValue = autoIncrementIDValue;
	}
}
