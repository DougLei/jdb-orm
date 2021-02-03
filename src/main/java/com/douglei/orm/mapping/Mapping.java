package com.douglei.orm.mapping;

import java.io.Serializable;

/**
 * 
 * @author DougLei
 */
public interface Mapping extends Serializable{
	
	/**
	 * 获取id
	 * @return
	 */
	String getId();
	
	/**
	 * 获取type
	 * @return
	 */
	String getType();
}
