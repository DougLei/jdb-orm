package com.douglei.orm.core.metadata.sql;

import java.util.Date;
import com.douglei.tools.utils.IdentityUtil;

/**
 * sql参数默认值处理器
 * @author DougLei
 */
class DefaultValueHandler {

	/**
	 * 获取默认值
	 * @param defaultValue
	 * @return
	 */
	public static Object getDefaultValue(String defaultValue) {
		if(defaultValue != null && defaultValue.charAt(0) == '_') {
			if(defaultValue.equals("_currentDate")) {
				return new Date();
			} else if(defaultValue.equals("_uuid32")) {
				return IdentityUtil.get32UUID();
			} else if(defaultValue.equals("_uuid36")) {
				return IdentityUtil.getUUID();
			}
		}
		return defaultValue;
	}
}
