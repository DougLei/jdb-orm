package com.douglei.orm.configuration.environment;

import java.util.Map;

import com.douglei.orm.mapping.impl.table.metadata.CreateMode;

/**
 * 
 * @author DougLei
 */
public class EnvironmentProperty {
	private boolean enableStatementCache = true;
	private boolean enableTableSessionCache;
	private CreateMode globalCreateMode;
	
	EnvironmentProperty(Map<String, String> propertyMap) {
		if(propertyMap != null) {
			this.enableStatementCache = !"false".equalsIgnoreCase(propertyMap.get("enableStatementCache"));
			this.enableTableSessionCache = "true".equalsIgnoreCase(propertyMap.get("enableTableSessionCache"));
			this.globalCreateMode = CreateMode.toValue(propertyMap.get("globalCreateMode"));
		}
	}
	
	/**
	 * 是否启用Statement缓存
	 */
	public boolean enableStatementCache() {
		return enableStatementCache;
	}
	
	/**
	 * 是否启用TableSession缓存
	 */
	public boolean enableTableSessionCache() {
		return enableTableSessionCache;
	}
	
	/**
	 * 获取全局的CreateMode
	 */
	public CreateMode getGlobalCreateMode() {
		return globalCreateMode;
	}
}
