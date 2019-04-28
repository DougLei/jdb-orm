package com.douglei.session;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.utils.StringUtil;

/**
 * 实体map
 * @author DougLei
 */
public class EntityMap {
	private static final Logger logger = LoggerFactory.getLogger(EntityMap.class);
	
	private String name;
	private Map<String, Object> map;
	
	public EntityMap(String name) {
		if(StringUtil.isEmpty(name)) {
			logger.error("{} 的name属性值不能为空", getClass());
			throw new NullPointerException(getClass() + " 的name属性值不能为空");
		}
		this.name = name;
		this.map = new HashMap<String, Object>();
	}
	
	/**
	 * 设置属性值
	 * @param propertyName
	 * @param propertyValue
	 */
	public EntityMap setProperty(String propertyName, Object propertyValue) {
		map.put(propertyName, propertyValue);
		return this;
	}
	
	public String getName() {
		return name;
	}
	public Object getPropertyValue(String propertyName) {
		return map.get(propertyName);
	}
}
