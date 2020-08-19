package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.metadata.table.UniqueConstraint;
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
	
	protected List<UniqueConstraint> uniqueConstraints;// 唯一约束集合
	
	public AbstractPersistentObject(TableMetadata tableMetadata) {
		this.tableMetadata = tableMetadata;
		this.uniqueConstraints = tableMetadata.getUniqueConstraints();
	}
	public AbstractPersistentObject(TableMetadata tableMetadata, Object originObject) {
		this(tableMetadata);
		setOriginObject(originObject);
	}
	
	public String getCode() {
		return tableMetadata.getCode();
	}
	public Object getOriginObject() {
		return originObject;
	}
	// 是否存在唯一约束
	public boolean existsUniqueConstraint() {
		return uniqueConstraints != null;
	}
	/**
	 * 获取持久化对象的唯一值实例
	 * 调用该方法前, 必须要先使用 {@link AbstractPersistentObject#existsUniqueColumn()} 方法判断一下, 如果返回true才能调用该方法
	 * @return {@link UniqueValue} / {@link List<UniqueValue>}
	 */
	protected Object getPersistentObjectUniqueValue(){
		if(uniqueConstraints.size() == 1) {
			return new UniqueValue(objectMap, uniqueConstraints.get(0));
		}else {
			List<UniqueValue> currentPersistentObjectUniqueValues = new ArrayList<UniqueValue>(uniqueConstraints.size());
			uniqueConstraints.forEach(uc -> currentPersistentObjectUniqueValues.add(new UniqueValue(objectMap, uc)));
			return currentPersistentObjectUniqueValues;
		}
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
