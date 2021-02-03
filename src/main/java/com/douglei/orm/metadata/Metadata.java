package com.douglei.orm.metadata;

import java.io.Serializable;

/**
 * 元数据
 * @author DougLei
 */
public interface Metadata extends Serializable{
	
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
