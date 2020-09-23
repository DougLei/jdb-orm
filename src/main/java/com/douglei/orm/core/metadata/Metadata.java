package com.douglei.orm.core.metadata;

import java.io.Serializable;

/**
 * 
 * @author DougLei
 */
public interface Metadata extends Serializable{
	
	/**
	 * 唯一编码值
	 * @return
	 */
	String getCode();
}
