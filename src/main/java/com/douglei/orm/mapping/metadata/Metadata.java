package com.douglei.orm.mapping.metadata;

import java.io.Serializable;

/**
 * 
 * @author DougLei
 */
public interface Metadata extends Serializable{
	
	/**
	 * 
	 * @return
	 */
	default String getCode() {
		throw new NullPointerException("没有获取到唯一编码值");
	}
}
