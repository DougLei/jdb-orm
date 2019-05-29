package com.douglei.database.metadata.table.column.extend;

/**
 * 不支持的constraint数据类型异常
 * @author DougLei
 */
public class UnsupportConstraintDataTypeException extends RuntimeException{
	private static final long serialVersionUID = -6036886554059655075L;

	public UnsupportConstraintDataTypeException(String message) {
		super(message);
	}
}
