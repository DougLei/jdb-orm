package com.douglei.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.metadata.table.ColumnMetadata;
import com.douglei.database.metadata.table.TableMetadata;
import com.douglei.utils.StringUtil;

/**
 * 实体map
 * @author DougLei
 */
public class EntityMap {
	private static final Logger logger = LoggerFactory.getLogger(EntityMap.class);
	
	private String name;
	private Map<String, Object> propertyMap;
	
	public EntityMap(String name) {
		this(name, new HashMap<String, Object>());
	}
	public EntityMap(String name, Map<String, Object> propertyMap) {
		if(StringUtil.isEmpty(name)) {
			logger.error("{} 的name属性值不能为空", getClass());
			throw new NullPointerException(getClass() + " 的name属性值不能为空");
		}
		this.name = name;
		this.propertyMap = propertyMap;
	}

	/**
	 * 设置属性值
	 * @param propertyName
	 * @param propertyValue
	 */
	public EntityMap setProperty(String propertyName, Object propertyValue) {
		propertyMap.put(propertyName, propertyValue);
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * 从propertyMap中筛选出columnMetadataCodes的数据
	 * @param tableMetadata
	 * @return
	 */
	public Map<String, Object> filterColumnMetadataPropertyMap(TableMetadata tableMetadata) {
		Set<String> columnMetadataCodes = tableMetadata.getColumnMetadataCodes();
		Map<String, Object> resultMap = new HashMap<String, Object>(columnMetadataCodes.size());
		
		Object value = null;
		ColumnMetadata columnMetadata = null;
		Set<String> propertyMapKeys = propertyMap.keySet();
		for (String pmkey : propertyMapKeys) {
			value = propertyMap.get(pmkey);
			if(value != null) {
				columnMetadata = tableMetadata.getColumnMetadata(pmkey);
				if(columnMetadata != null) {
					resultMap.put(columnMetadata.getCode(), value);
				}
			}
		}
		return resultMap;
	}
}
