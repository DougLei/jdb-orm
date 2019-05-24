package com.douglei.database.metadata.sql.content.node;

/**
 * 不支持的collection类型异常
 * @author DougLei
 */
public class UnsupportCollectionTypeException extends RuntimeException{
	private static final long serialVersionUID = 5978108756255900836L;

	public UnsupportCollectionTypeException(String message) {
		super(message);
	}
}
