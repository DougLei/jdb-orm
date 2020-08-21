package com.douglei.orm.configuration.impl.xml.element.environment.property;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.orm.configuration.environment.DatabaseMetadata;
import com.douglei.orm.configuration.environment.mapping.store.MappingStore;
import com.douglei.orm.configuration.environment.mapping.store.impl.ApplicationMappingStore;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.configuration.environment.property.FieldMetaData;
import com.douglei.orm.core.dialect.Dialect;
import com.douglei.orm.core.dialect.DialectMapping;
import com.douglei.orm.core.metadata.sql.DefaultValueHandler;
import com.douglei.orm.core.metadata.sql.SqlParameterConfigHolder;
import com.douglei.orm.core.metadata.table.CreateMode;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;
import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * <environment>节点下所有的<property>节点
 * @author DougLei
 */
public class XmlEnvironmentProperty implements EnvironmentProperty{
	
	private Map<String, String> propertyMap;
	private boolean propertyMapEmpty;
	private String id;
	private DatabaseMetadata databaseMetadata;
	
	@FieldMetaData
	private Dialect dialect;
	
	@FieldMetaData
	private boolean enableStatementCache = true;
	
	@FieldMetaData
	private boolean enableTableSessionCache;
	
	@FieldMetaData
	private MappingStore mappingStore;
	
	@FieldMetaData
	private boolean clearMappingStoreOnStart = true;
	
	@FieldMetaData
	private CreateMode tableCreateMode;
	
	@FieldMetaData
	private boolean enableDataValidate;
	
	@FieldMetaData
	private boolean enableTableDynamicUpdate;
	
	@FieldMetaData
	private String serializationFileRootPath;
	
	@FieldMetaData
	private boolean enableColumnDynamicUpdateValidate;
	
	@FieldMetaData
	private byte dynamicMappingOnceMaxCount=10;
	
	@FieldMetaData
	private String sqlParameterPrefix="#{";
	@FieldMetaData
	private String sqlParameterSuffix="}";
	@FieldMetaData
	private String sqlParameterSplit=",";
	@FieldMetaData
	private DefaultValueHandler sqlParameterDefaultValueHandler;

	private SqlParameterConfigHolder sqlParameterConfigHolder;
	
	public XmlEnvironmentProperty(String id, Map<String, String> propertyMap, DatabaseMetadata databaseMetadata, MappingStore mappingStore) {
		this.id = id;
		this.propertyMap = propertyMap;
		this.propertyMapEmpty = propertyMap==null || propertyMap.isEmpty();
		this.databaseMetadata = databaseMetadata;
		this.mappingStore = mappingStore;
		
		Field[] fields = this.getClass().getDeclaredFields();
		List<String> fieldNames = doSelfChecking(fields);
		setFieldValues(fieldNames);
		checkFiledValues();
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
		byte fieldNameIndex = 0;
		List<String> fieldNames = new ArrayList<String>(fields.length);
		FieldMetaData fieldMetadata = null;
		for (Field field : fields) {
			fieldMetadata = field.getAnnotation(FieldMetaData.class);
			if(fieldMetadata != null) {
				if(fieldMetadata.isRequired() && (propertyMapEmpty || StringUtil.isEmpty(propertyMap.get(fieldNames.get(fieldNameIndex))))) {
					throw new NullPointerException(fieldMetadata.isnullOfErrorMessage());
				}
				fieldNames.add(field.getName());
				fieldNameIndex++;
			}
		}
		return fieldNames;
	}

	/**
	 * 根据fieldName，调用对应的set方法
	 * @param fieldNames
	 */
	private void setFieldValues(List<String> fieldNames) {
		Class<?> clz = this.getClass();
		String value = null;
		try {
			for (String fieldName : fieldNames) {
				value = propertyMapEmpty?null:propertyMap.get(fieldName);
				clz.getDeclaredMethod("set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1), String.class).invoke(this, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 校验属性值
	 */
	private void checkFiledValues() {
		if(enableTableDynamicUpdate && serializationFileRootPath == null) 
			setSerializationFileRootPath(System.getProperty("user.home"));
		sqlParameterConfigHolder = new SqlParameterConfigHolder(sqlParameterPrefix, sqlParameterSuffix, sqlParameterSplit, sqlParameterDefaultValueHandler);
	}

	void setDialect(String value) {
		if(StringUtil.notEmpty(value))
			this.dialect = DialectMapping.getDialect(value, databaseMetadata);
	}
	void setEnableStatementCache(String value) {
		if(VerifyTypeMatchUtil.isBoolean(value)) {
			this.enableStatementCache = Boolean.parseBoolean(value);
		}
	}
	void setEnableTableSessionCache(String value) {
		if(VerifyTypeMatchUtil.isBoolean(value)) {
			this.enableTableSessionCache = Boolean.parseBoolean(value);
		}
	}
	void setMappingStore(String value) {
		if(this.mappingStore == null) 
			this.mappingStore = new ApplicationMappingStore();
	}
	public void setClearMappingStoreOnStart(String value) {
		if(VerifyTypeMatchUtil.isBoolean(value))
			this.clearMappingStoreOnStart = Boolean.parseBoolean(value);
	}
	void setTableCreateMode(String value) {
		if(StringUtil.notEmpty(value)) 
			this.tableCreateMode = CreateMode.toValue(value);
	}
	void setEnableDataValidate(String value) {
		if(VerifyTypeMatchUtil.isBoolean(value)) 
			this.enableDataValidate = Boolean.parseBoolean(value);
	}
	void setEnableTableDynamicUpdate(String value) {
		if(VerifyTypeMatchUtil.isBoolean(value)) 
			this.enableTableDynamicUpdate = Boolean.parseBoolean(value);
	}
	void setSerializationFileRootPath(String value) {
		if(StringUtil.notEmpty(value)) 
			this.serializationFileRootPath = value;
	}
	void setEnableColumnDynamicUpdateValidate(String value) {
		if(VerifyTypeMatchUtil.isBoolean(value)) 
			this.enableColumnDynamicUpdateValidate = Boolean.parseBoolean(value);
	}
	void setDynamicMappingOnceMaxCount(String value) {
		if(VerifyTypeMatchUtil.isLimitByte(value)) 
			this.dynamicMappingOnceMaxCount = Byte.parseByte(value);
	}
	void setSqlParameterPrefix(String value) {
		if(StringUtil.notEmpty(value))
			this.sqlParameterPrefix = value;
	}
	void setSqlParameterSuffix(String value) {
		if(StringUtil.notEmpty(value))
			this.sqlParameterSuffix = value;
	}
	void setSqlParameterSplit(String value) {
		if(StringUtil.notEmpty(value))
			this.sqlParameterSplit = value;
	}
	void setSqlParameterDefaultValueHandler(String value) {
		if(StringUtil.isEmpty(value))
			this.sqlParameterDefaultValueHandler = new DefaultValueHandler();
		else
			this.sqlParameterDefaultValueHandler = (DefaultValueHandler) ConstructorUtil.newInstance(value);
	}

	// 根据数据库元数据, 获取对应的dialect
	public void setDialectByDatabaseMetadata(DatabaseMetadata databaseMetadata) {
		this.dialect = DialectMapping.getDialectByDatabaseMetadata(databaseMetadata);
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
	public boolean enableStatementCache() {
		return enableStatementCache;
	}
	@Override
	public boolean enableTableSessionCache() {
		return enableTableSessionCache;
	}
	@Override
	public MappingStore getMappingStore() {
		return mappingStore;
	}
	@Override
	public boolean clearMappingStoreOnStart() {
		return clearMappingStoreOnStart;
	}
	@Override
	public CreateMode getTableCreateMode() {
		return tableCreateMode;
	}
	@Override
	public boolean enableDataValidate() {
		return enableDataValidate;
	}
	@Override
	public boolean enableTableDynamicUpdate() {
		return enableTableDynamicUpdate;
	}
	@Override
	public String getSerializationFileRootPath() {
		return serializationFileRootPath;
	}
	@Override
	public boolean enableColumnDynamicUpdateValidate() {
		return enableColumnDynamicUpdateValidate;
	}
	@Override
	public byte dynamicMappingOnceMaxCount() {
		return dynamicMappingOnceMaxCount;
	}
	@Override
	public SqlParameterConfigHolder getSqlParameterConfigHolder() {
		return sqlParameterConfigHolder;
	}
}
