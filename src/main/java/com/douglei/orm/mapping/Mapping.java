package com.douglei.orm.mapping;

import java.io.Serializable;

/**
 * 
 * @author DougLei
 */
public interface Mapping extends Serializable{
	
	/**
	 * 获取编码
	 * @return
	 */
	String getCode();
	
	/**
	 * 获取类型
	 * @return
	 */
	String getType();
}
