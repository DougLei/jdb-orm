package com.douglei.orm.mapping.metadata;

import com.douglei.orm.configuration.environment.CreateMode;

/**
 * 
 * @author DougLei
 */
public class AbstractDBObjectMetadata extends AbstractMetadata {
	protected CreateMode createMode;// 创建模式
	
	/**
	 * 获取创建模式
	 * @return
	 */
	public final CreateMode getCreateMode() {
		return createMode;
	}
}
