package com.douglei.orm.configuration.environment;

import java.util.Map;

/**
 * 
 * @author DougLei
 */
public class EnvironmentProperty {
	private CreateMode globalCreateMode;
	
	EnvironmentProperty(Map<String, String> propertyMap) {
		if(propertyMap != null) {
			this.globalCreateMode = CreateMode.toValue(propertyMap.get("globalCreateMode"));
		}
	}
	
	/**
	 * 获取全局的CreateMode
	 */
	public CreateMode getGlobalCreateMode() {
		return globalCreateMode;
	}
}
