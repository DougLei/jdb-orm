package com.douglei.orm.sessions.session.table.impl.persistent;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.context.RunMappingConfigurationContext;
import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.TableMetadata;
import com.douglei.orm.core.validate.ValidateException;
import com.douglei.orm.core.validate.ValidateProcessor;
import com.douglei.orm.sessions.session.execution.ExecutionHolder;
import com.douglei.orm.sessions.session.table.impl.persistent.execution.DeleteExecutionHolder;
import com.douglei.orm.sessions.session.table.impl.persistent.execution.InsertExecutionHolder;
import com.douglei.orm.sessions.session.table.impl.persistent.execution.UpdateExecutionHolder;
import com.douglei.orm.sessions.session.table.impl.persistent.id.Identity;
import com.douglei.tools.utils.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class PersistentObject {
	private static final Logger logger = LoggerFactory.getLogger(PersistentObject.class);
	
	private Object originObject;
	private OperationState operationState;
	
	private TableMetadata tableMetadata;
	private Map<String, Object> propertyMap;
	
	public PersistentObject(TableMetadata tableMetadata, Object originObject, OperationState operationState) {
		this.tableMetadata = tableMetadata;
		setOriginObject(originObject);
		setOperationState(operationState);
	}
	
	public String getCode() {
		return tableMetadata.getCode();
	}
	
	private Identity id;
	public Identity getId() {
		if(id == null) {
			if(tableMetadata.existsPrimaryKey()) {
				Set<String> primaryKeyColumnMetadataCodes = tableMetadata.getPrimaryKeyColumnMetadataCodes();
				Object id;
				if(primaryKeyColumnMetadataCodes.size() == 1) {
					id = propertyMap.get(primaryKeyColumnMetadataCodes.iterator().next());
				}else {
					Map<String, Object> idMap = new HashMap<String, Object>(primaryKeyColumnMetadataCodes.size());
					for (String pkCode : primaryKeyColumnMetadataCodes) {
						idMap.put(pkCode, propertyMap.get(pkCode));
					}
					id = idMap;
				}
				this.id = new Identity(id);
			}else {
				this.id = new Identity(propertyMap);// 不存在主键配置时, 就将整个对象做为id
			}
		}
		return id;
	}
	
	public void setId(Identity identity) {
		this.id = identity;
	}
	
	@Override
	public String toString() {
		return "\n"
				+ "operationState:" + operationState 
				+ "\n" 
				+ originObject.toString();
	}
	
	public ExecutionHolder getExecutionHolder() {
		switch(operationState) {
			case CREATE:
				return new InsertExecutionHolder(tableMetadata, propertyMap);
			case DELETE:
				return new DeleteExecutionHolder(tableMetadata, propertyMap);
			case UPDATE:
				return new UpdateExecutionHolder(tableMetadata, propertyMap);
		}
		return null;
	}
	
	public OperationState getOperationState() {
		return operationState;
	}
	public void setOperationState(OperationState operationState) {
		this.operationState = operationState;
	}
	
	public Object getOriginObject() {
		return originObject;
	}
	
	@SuppressWarnings("unchecked")
	public void setOriginObject(Object originObject) {
		if(originObject instanceof Map) {
			logger.debug("originObject is Map type, 从该map中, 筛选出相关列的数据信息");
			propertyMap = filterColumnMetadatasPropertyMap(tableMetadata, (Map<String, Object>)originObject);
		}else {
			logger.debug("originObject is Object type [{}], 从该object中, 通过java内省机制, 获取相关列的数据信息", originObject.getClass());
			propertyMap = IntrospectorUtil.getProperyValues(originObject, tableMetadata.getColumnMetadataCodes());
		}
		if(propertyMap == null || propertyMap.size() == 0) {
			throw new NullPointerException("要操作的数据不能为空");
		}
		doValidate();
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
		Set<String> columnMetadataCodes = tableMetadata.getColumnMetadataCodes();
		int columnSize = columnMetadataCodes.size();
		Map<String, Object> resultPropertyMap = new HashMap<String, Object>(columnSize);
		
		int index = 1;
		Set<String> originPropertyMapKeys = originPropertyMap.keySet();
		for (String originPMkey : originPropertyMapKeys) {
			if(tableMetadata.isColumnMetadata(originPMkey)) {
				resultPropertyMap.put(originPMkey, originPropertyMap.get(originPMkey));
				if(index == columnSize) {
					break;
				}
				index++;
			}
		}
		return resultPropertyMap;
	}
	
	// 进行验证
	private void doValidate() {
		if(tableMetadata.isValidateColumn()) {
			RunMappingConfigurationContext.setCurrentExecuteMappingDescription("执行code=["+tableMetadata.getCode()+"]的TABLE映射");
			
			Object value = null;
			ColumnMetadata validateColumn = null;
			String result = null;
			Set<String> keys = propertyMap.keySet();
			for (String key : keys) {
				if((validateColumn = tableMetadata.getValidateColumn(key)) != null) {
					value = propertyMap.get(key);
					
					if(!validateColumn.isNullabled() && value == null && validateColumn.getDefaultValue() == null) {
						throw new ValidateException(validateColumn.getDescriptionName(), validateColumn.getName(), "不能为空");
					}
					result = ValidateProcessor.validateValueDataType(value, validateColumn.getLength(), validateColumn.getPrecision(),validateColumn.getDataTypeHandler());
					if(result != null) {
						throw new ValidateException(validateColumn.getDescriptionName(), validateColumn.getName(), result);
					}
				}
			}
		}
	}
}
