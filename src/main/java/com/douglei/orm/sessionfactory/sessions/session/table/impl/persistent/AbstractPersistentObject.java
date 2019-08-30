package com.douglei.orm.sessionfactory.sessions.session.table.impl.persistent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	
	protected TableMetadata tableMetadata;
	protected Object originObject;// 要持久化操作的源对象
	protected Map<String, Object> propertyMap;// 将源对象转换为map集合
	
	protected List<String> validateUniqueColumnCodes;// 要验证的, 有唯一约束的列code集合
	
	public AbstractPersistentObject(TableMetadata tableMetadata) {
		this.tableMetadata = tableMetadata;
		this.validateUniqueColumnCodes = tableMetadata.getValidateUniqueColumnCodes();
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
	public Map<String, Object> getTargetPropertyMap(){
		return propertyMap;
	}
	/**
	 * 是否存在要验证的, 有唯一约束的列
	 * @return
	 */
	public boolean existsValidateUniqueColumns() {
		return validateUniqueColumnCodes != null;
	}
	/**
	 * 调用该方法前, 必须要先使用 {@link AbstractPersistentObject#existsValidateUniqueColumns()} 方法判断一下, 如果返回true才能调用该方法
	 * 获取持久化对象要验证的唯一值数据, 如果只有一个要验证唯一值的列, 则直接返回该列值, 否则返回List<Object>集合
	 * @return
	 */
	protected Object getPersistentObjectValidateUniqueValue(){
		if(validateUniqueColumnCodes.size() == 1) {
			return propertyMap.get(validateUniqueColumnCodes.get(0));
		}else {
			List<Object> currentPersistentObjectUniqueValues = new ArrayList<Object>(validateUniqueColumnCodes.size());
			validateUniqueColumnCodes.forEach(ucc -> currentPersistentObjectUniqueValues.add(propertyMap.get(ucc)));
			return currentPersistentObjectUniqueValues;
		}
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
