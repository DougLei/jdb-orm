package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.tools.utils.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class AbstractPersistentObject {
	private static final Logger logger = LoggerFactory.getLogger(AbstractPersistentObject.class);
	
	protected Object originObject;
	protected TableMetadata tableMetadata;
	protected Map<String, Object> propertyMap;
	
	public AbstractPersistentObject(TableMetadata tableMetadata) {
		this.tableMetadata = tableMetadata;
	}
	public AbstractPersistentObject(TableMetadata tableMetadata, Object originObject) {
		this.tableMetadata = tableMetadata;
		setOriginObject(originObject);
	}
	
	public Object getOriginObject() {
		return originObject;
	}
	public Map<String, Object> getTargetPropertyMap(){
		return propertyMap;
	}
	
	@SuppressWarnings("unchecked")
	public void setOriginObject(Object originObject) {
		if(originObject instanceof Map) {
			logger.debug("originObject is Map type, 从该map中, 筛选出相关列的数据信息");
			propertyMap = filterColumnMetadatasPropertyMap(tableMetadata, (Map<String, Object>)originObject);
		}else {
			logger.debug("originObject is Object type [{}], 从该object中, 通过java内省机制, 获取相关列的数据信息", originObject.getClass());
			propertyMap = IntrospectorUtil.getProperyValues(originObject, tableMetadata.getColumnCodes());
		}
		if(propertyMap == null || propertyMap.size() == 0) {
			throw new NullPointerException("要操作的数据不能为空");
		}
		if(logger.isDebugEnabled()) {
			logger.debug("获取的最终propertyMap为: {}", propertyMap.toString());
		}
		this.originObject = originObject;
	}
	
	/**
	 * 从originPropertyMap集合中, 筛选出相关列的数据信息
	 * @param tableMetadata
	 * @param originPropertyMap 
	 * @return
	 */
	private Map<String, Object> filterColumnMetadatasPropertyMap(TableMetadata tableMetadata, Map<String, Object> originPropertyMap) {
		Set<String> columnMetadataCodes = tableMetadata.getColumnCodes();
		int columnSize = columnMetadataCodes.size();
		Map<String, Object> resultPropertyMap = new HashMap<String, Object>(columnSize);
		
		int index = 1;
		Set<String> originPropertyMapKeys = originPropertyMap.keySet();
		for (String originPMkey : originPropertyMapKeys) {
			if(tableMetadata.isColumnByCode(originPMkey)) {
				resultPropertyMap.put(originPMkey, originPropertyMap.get(originPMkey));
				if(index == columnSize) {
					break;
				}
				index++;
			}
		}
		return resultPropertyMap;
	}
}
