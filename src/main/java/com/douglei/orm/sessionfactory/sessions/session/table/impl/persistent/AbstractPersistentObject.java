package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.mapping.impl.table.metadata.TableMetadata;
import com.douglei.tools.utils.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class AbstractPersistentObject {
	private static final Logger logger = LoggerFactory.getLogger(AbstractPersistentObject.class);
	
	protected TableMetadata tableMetadata;
	protected Object originObject;// 要持久化操作的源对象
	protected Map<String, Object> objectMap;// 将源对象转换为map集合
	
	public AbstractPersistentObject(TableMetadata tableMetadata) {
		this.tableMetadata = tableMetadata;
	}
	public AbstractPersistentObject(TableMetadata tableMetadata, Object originObject) {
		this(tableMetadata);
		setOriginObject(originObject);
	}
	
	public String getCode() {
		return tableMetadata.getCode();
	}
	public TableMetadata getTableMetadata() {
		return tableMetadata;
	}
	public Object getOriginObject() {
		return originObject;
	}
	
	@SuppressWarnings("unchecked")
	public void setOriginObject(Object originObject) {
		if(originObject instanceof Map) {
			logger.debug("originObject is Map type, 从该map中, 筛选出相关列的数据信息");
			objectMap = filterColumnMetadatasPropertyMap(tableMetadata, (Map<String, Object>)originObject);
		}else {
			logger.debug("originObject is Object type [{}], 从该object中, 通过java内省机制, 获取相关列的数据信息", originObject.getClass());
			objectMap = IntrospectorUtil.getProperyValues(originObject, tableMetadata.getColumnCodes());
		}
		if(objectMap == null || objectMap.size() == 0) {
			throw new NullPointerException("要操作的数据不能为空");
		}
		if(logger.isDebugEnabled()) {
			logger.debug("获取的最终objectMap为: {}", objectMap.toString());
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
		short columnSize = (short) columnMetadataCodes.size();
		Map<String, Object> objectMap = new HashMap<String, Object>(columnSize);
		
		int index = 1;
		Set<String> originPropertyMapKeys = originPropertyMap.keySet();
		for (String originPMkey : originPropertyMapKeys) {
			if(tableMetadata.isColumnByCode(originPMkey)) {
				objectMap.put(originPMkey, originPropertyMap.get(originPMkey));
				if(index == columnSize) {
					break;
				}
				index++;
			}
		}
		return objectMap;
	}
}
