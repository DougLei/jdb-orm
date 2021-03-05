package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.mapping.impl.table.metadata.ColumnMetadata;
import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.orm.sessionfactory.sessions.SessionExecutionException;
import com.douglei.tools.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class AbstractPersistentObject {
	private static final Logger logger = LoggerFactory.getLogger(AbstractPersistentObject.class);
	
	protected TableMetadata tableMetadata;
	protected Object originObject;// 要要操作的源数据实例
	protected Map<String, Object> objectMap;// 将源数据转换为map集合
	
	protected AbstractPersistentObject(TableMetadata tableMetadata) {
		this.tableMetadata = tableMetadata;
	}
	
	/**
	 * 设置要操作的源数据实例
	 * @param originObject
	 */
	@SuppressWarnings("unchecked")
	public void setOriginObject(Object originObject) {
		logger.debug("解析originObject, 获取objectMap");
		if(originObject instanceof Map) {
			logger.debug("originObject is Map");
			objectMap = extract(tableMetadata.getColumnMap4Code(), (Map<String, Object>)originObject);
		}else {
			logger.debug("originObject is Object");
			objectMap = IntrospectorUtil.getValues(tableMetadata.getColumnMap4Code().keySet(), originObject);
		}
		
		logger.debug("解析originObject, 获取的objectMap为: {}", objectMap);
		if(objectMap.isEmpty()) 
			throw new SessionExecutionException("要操作的数据不能为空");
		this.originObject = originObject;
	}
	
	// 从originObject中提取出相关的objectMap集合
	private Map<String, Object> extract(Map<String, ColumnMetadata> columnMapByCode, Map<String, Object> originObject) {
		Map<String, Object> objectMap = new HashMap<String, Object>();
		
		int index = 1;
		for (String key : originObject.keySet()) {
			if(columnMapByCode.containsKey(key)) {
				objectMap.put(key, originObject.get(key));
				
				if(index == columnMapByCode.size()) 
					break;
				index++;
			}
		}
		return objectMap;
	}
	
	/**
	 * 获取TableMetadata实例
	 * @return
	 */
	public TableMetadata getTableMetadata() {
		return tableMetadata;
	}
	
	/**
	 * 获取要操作的源数据实例
	 * @return
	 */
	public Object getOriginObject() {
		return originObject;
	}
}
