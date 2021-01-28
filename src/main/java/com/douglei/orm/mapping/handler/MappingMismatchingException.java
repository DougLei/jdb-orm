package com.douglei.orm.mapping.handler;

/**
 * mapping不匹配异常
 * @author DougLei
 */
public class MappingMismatchingException extends RuntimeException{
	private static final long serialVersionUID = -7336676451724229875L;

	public MappingMismatchingException(String code, String type) {
		super("code为"+code+"的mapping不是["+type+"]类型");
	}
}
