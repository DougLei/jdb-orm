package com.douglei.orm.mapping.handler;

/**
 * mapping不匹配异常
 * @author DougLei
 */
public class MappingMismatchingException extends RuntimeException{
	private static final long serialVersionUID = 6743424796837203072L;

	public MappingMismatchingException(String message) {
		super(message);
	}
}
