package com.douglei.orm.configuration.impl.xml.element.environment.property;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.orm.configuration.environment.DatabaseMetadata;
import com.douglei.orm.configuration.environment.mapping.cache.store.MappingCacheStore;
import com.douglei.orm.configuration.environment.mapping.cache.store.MappingCacheStoreMap;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.configuration.environment.property.FieldMetaData;
import com.douglei.orm.configuration.extconfiguration.datatypehandler.ExtDataTypeHandler;
import com.douglei.orm.core.dialect.Dialect;
import com.douglei.orm.core.dialect.DialectMapping;
import com.douglei.orm.core.metadata.table.CreateMode;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.datatype.ValidationUtil;

/**
 * <environment>节点下所有的<property>节点
 * @author DougLei
 */
public class XmlEnvironmentProperty implements EnvironmentProperty{
	
	private Map<String, String> propertyMap;
	private boolean propertyMapIsEmpty;
	private String id;
	
	@FieldMetaData
	private Dialect dialect;
	
	@FieldMetaData
	private boolean enableSessionCache;
	
	@FieldMetaData
	private boolean enableTableSessionCache;
	
	@FieldMetaData
	private MappingCacheStore mappingCacheStore;
	
	@FieldMetaData
	private CreateMode tableCreateMode;
	
	@FieldMetaData
	private boolean enableDataValidation;
	
	@FieldMetaData
	private boolean enableTableDynamicUpdate;
	
	@FieldMetaData
	private String serializationFileRootPath;
	
	@FieldMetaData
	private boolean enableColumnDynamicUpdateValidation;
	
	public XmlEnvironmentProperty(String id, Map<String, String> propertyMap) {
		this.id = id;
		this.propertyMap = propertyMap;
		this.propertyMapIsEmpty = (propertyMap == null || propertyMap.size() == 0);
		
		Field[] fields = this.getClass().getDeclaredFields();
		List<String> fieldNames = doSelfChecking(fields);
		invokeSetMethodByFieldName(fieldNames);
		validateFiledValue();
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
	
	/**
	 * 验证属性值
	 */
	private void validateFiledValue() {
		if(enableTableDynamicUpdate && serializationFileRootPath == null) {
			throw new NullPointerException("当开启了表动态更新[<property name=\"enableTableDynamicUpdate\" value=\"true\" />]的功能时, 必须配置<property name=\"serializationFileRootPath\" value=\"\" />的值, 标明序列化文件的根路径 [配置的路径从磁盘根路径起]");
		}
	}

	void setDialect(String value) {
		if(StringUtil.notEmpty(value)) {
			this.dialect = DialectMapping.getDialect(value);
		}
	}
	void setEnableSessionCache(String value) {
		if(ValidationUtil.isBoolean(value)) {
			this.enableSessionCache = Boolean.parseBoolean(value);
			return;
		}
		this.enableSessionCache = true;
	}
	void setEnableTableSessionCache(String value) {
		if(ValidationUtil.isBoolean(value)) {
			this.enableTableSessionCache = Boolean.parseBoolean(value);
			return;
		}
		this.enableTableSessionCache = true;
	}
	private static final String DEFAULT_MAPPING_CACHE_STORE = "application";// 默认为 ApplicationMappingCacheStore
	void setMappingCacheStore(String value) {
		if(StringUtil.isEmpty(value)) {
			value = DEFAULT_MAPPING_CACHE_STORE;
		}
		this.mappingCacheStore = MappingCacheStoreMap.getMappingCacheStore(value);
	}
	void setTableCreateMode(String value) {
		if(StringUtil.notEmpty(value)) {
			this.tableCreateMode = CreateMode.toValue(value);
		}
	}
	void setEnableDataValidation(String value) {
		if(ValidationUtil.isBoolean(value)) {
			this.enableDataValidation = Boolean.parseBoolean(value);
		}
	}
	void setEnableTableDynamicUpdate(String value) {
		if(ValidationUtil.isBoolean(value)) {
			this.enableTableDynamicUpdate = Boolean.parseBoolean(value);
		}
	}
	void setSerializationFileRootPath(String value) {
		if(StringUtil.notEmpty(value)) {
			this.serializationFileRootPath = value;
		}
	}
	void setEnableColumnDynamicUpdateValidation(String value) {
		if(ValidationUtil.isBoolean(value)) {
			this.enableColumnDynamicUpdateValidation = Boolean.parseBoolean(value);
		}
	}

	// 根据数据库元数据, 获取对应的dialect
	public void setDialectByDatabaseMetadata(DatabaseMetadata databaseMetadata) {
		this.dialect = DialectMapping.getDialectByDatabaseMetadata(databaseMetadata);
	}
	
	// 设置扩展的数据类型Handler
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
	public String getId() {
		return id;
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
	public boolean getEnableTableSessionCache() {
		return enableTableSessionCache;
	}
	@Override
	public MappingCacheStore getMappingCacheStore() {
		return mappingCacheStore;
	}
	@Override
	public CreateMode getTableCreateMode() {
		return tableCreateMode;
	}
	@Override
	public boolean getEnableDataValidation() {
		return enableDataValidation;
	}
	@Override
	public boolean getEnableTableDynamicUpdate() {
		return enableTableDynamicUpdate;
	}
	@Override
	public String getSerializationFileRootPath() {
		return serializationFileRootPath;
	}
	@Override
	public boolean getEnableColumnDynamicUpdateValidation() {
		return enableColumnDynamicUpdateValidation;
	}
}
