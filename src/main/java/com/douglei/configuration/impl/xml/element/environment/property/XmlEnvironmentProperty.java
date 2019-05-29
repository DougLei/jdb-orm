package com.douglei.configuration.impl.xml.element.environment.property;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.configuration.environment.property.FieldMetaData;
import com.douglei.configuration.environment.property.mapping.store.target.MappingCacheStore;
import com.douglei.configuration.environment.property.mapping.store.target.MappingCacheStoreMap;
import com.douglei.configuration.extconfiguration.datatypehandler.ExtDataTypeHandler;
import com.douglei.configuration.impl.xml.element.environment.ReflectInvokeMethodException;
import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.DialectMapping;
import com.douglei.database.metadata.table.CreateMode;
import com.douglei.utils.StringUtil;
import com.douglei.utils.datatype.ValidationUtil;

/**
 * <environment>节点下所有的<property>节点
 * @author DougLei
 */
public class XmlEnvironmentProperty implements EnvironmentProperty{
	
	private Map<String, String> propertyMap;
	private boolean propertyMapIsEmpty;
	
	
	@FieldMetaData
	private Dialect dialect;
	
	@FieldMetaData
	private boolean enableSessionCache;
	
	@FieldMetaData
	private MappingCacheStore mappingCacheStore;
	
	@FieldMetaData
	private CreateMode tableCreateMode;
	
	public XmlEnvironmentProperty(Map<String, String> propertyMap) {
		this.propertyMap = propertyMap;
		this.propertyMapIsEmpty = (propertyMap == null || propertyMap.size() == 0);
		
		Field[] fields = this.getClass().getDeclaredFields();
		List<String> fieldNames = doSelfChecking(fields);
		invokeSetMethodByFieldName(fieldNames);
	}
	
	/**
	 * 自检
	 * <pre>
	 * 	判断是否有必填的属性，但是没有配置值
	 * </pre>
	 * @param fields
	 * @return 
	 */
	private List<String> doSelfChecking(Field[] fields) {
		int fieldNameIndex = 0;
		List<String> fieldNames = new ArrayList<String>(fields.length);
		FieldMetaData fieldMetadata = null;
		for (Field field : fields) {
			fieldMetadata = field.getAnnotation(FieldMetaData.class);
			if(fieldMetadata != null) {
				fieldNames.add(field.getName());
				if(fieldMetadata.isRequired() && (propertyMapIsEmpty || StringUtil.isEmpty(propertyMap.get(fieldNames.get(fieldNameIndex))))) {
					throw new NullPointerException(fieldMetadata.isnullOfErrorMessage());
				}
				fieldNameIndex++;
			}
		}
		return fieldNames;
	}

	/**
	 * 根据fieldName，调用对应的set方法
	 * @param fieldNames
	 */
	private void invokeSetMethodByFieldName(List<String> fieldNames) {
		Class<?> clz = getClass();
		String value = null;
		String fieldName_ = null;
		try {
			for (String fieldName : fieldNames) {
				fieldName_ = fieldName;
				
				value = propertyMapIsEmpty?null:propertyMap.get(fieldName);
				clz.getDeclaredMethod(fieldNameToSetMethodName(fieldName), String.class).invoke(this, value);
			}
		} catch (Exception e) {
			throw new ReflectInvokeMethodException("反射调用 class=["+clz.toString()+"], methodName=["+fieldNameToSetMethodName(fieldName_)+"] 时出现异常", e);
		}
	}
	private String fieldNameToSetMethodName(String fieldName) {
		return "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
	}

	void setDialect(String value) {
		if(StringUtil.isEmpty(value)) {
			return;
		}
		this.dialect = DialectMapping.getDialect(value);
	}
	void setEnableSessionCache(String value) {
		if(StringUtil.notEmpty(value) && ValidationUtil.isBoolean(value)) {
			this.enableSessionCache = Boolean.parseBoolean(value);
			return;
		}
		this.enableSessionCache = true;
	}
	void setMappingCacheStore(String value) {
		if(StringUtil.isEmpty(value)) {
			value = "application";// 使用默认的 ApplicationMappingCacheStore
		}
		this.mappingCacheStore = MappingCacheStoreMap.getMappingCacheStore(value);
	}
	void setTableCreateMode(String value) {
		if(StringUtil.notEmpty(value)) {
			this.tableCreateMode = CreateMode.toValue(value);
		}
		if(this.tableCreateMode == null) {
			this.tableCreateMode = CreateMode.NONE;
		}
	}

	public void setDialectByJDBCUrl(String JDBCUrl) {
		this.dialect = DialectMapping.getDialectByJDBCUrl(JDBCUrl);
	}
	public void setExtDataTypeHandlers(List<ExtDataTypeHandler> extDataTypeHandlerList) {
		if(extDataTypeHandlerList != null  && extDataTypeHandlerList.size() > 0) {
			Dialect dialect = null;
			for (ExtDataTypeHandler extDataTypeHandler : extDataTypeHandlerList) {
				dialect = extDataTypeHandler.getDialect();
				if(dialect == null) {
					dialect = this.dialect;
				}
				dialect.getDataTypeHandlerMapping().registerExtDataTypeHandler(extDataTypeHandler);
			}
		}
	}
	
	@Override
	public Dialect getDialect() {
		return dialect;
	}
	@Override
	public boolean getEnableSessionCache() {
		return enableSessionCache;
	}
	@Override
	public MappingCacheStore getMappingCacheStore() {
		return mappingCacheStore;
	}
	@Override
	public CreateMode getTableCreateMode() {
		return tableCreateMode;
	}
}
