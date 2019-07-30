package com.douglei.orm.core.metadata.table;

import com.douglei.tools.utils.StringUtil;

/**
 * 主键类型
 * @author DougLei
 */
public enum PrimaryKeyType {

	/**
	 * 用户自定义, 即需要用户手动进行setId()操作
	 */
	USER,
	/**
	 * uuid 36位
	 */
	UUID36,
	/**
	 * uuid 32位
	 */
	UUID32,
	/**
	 * 自增主键
	 */
	INCREMENT;
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	public static PrimaryKeyType toValue(String type) {
		if(StringUtil.notEmpty(type)) {
			type = type.toUpperCase();
			PrimaryKeyType[] pkvts = PrimaryKeyType.values();
			for (PrimaryKeyType pkvt : pkvts) {
				if(pkvt.name().equals(type)) {
					return pkvt;
				}
			}
		}
		return USER;
	}
}
