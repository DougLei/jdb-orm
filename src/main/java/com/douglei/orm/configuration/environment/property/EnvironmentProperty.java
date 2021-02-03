package com.douglei.orm.configuration.environment.property;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.orm.dialect.Dialect;
import com.douglei.orm.dialect.DialectContainer;
import com.douglei.orm.dialect.DialectKey;
import com.douglei.orm.mapping.container.ApplicationMappingContainer;
import com.douglei.orm.mapping.container.MappingContainer;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.DefaultValueHandler;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterConfigHolder;
import com.douglei.orm.mapping.impl.table.metadata.CreateMode;
import com.douglei.tools.StringUtil;
import com.douglei.tools.datatype.DataTypeValidateUtil;
import com.douglei.tools.reflect.ClassUtil;

/**
 * <environment>节点下所有的<property>节点
 * @author DougLei
 */
public class EnvironmentProperty {
	private String id;
	private Dialect dialect;
	private MappingContainer mappingContainer;
	
	@IsField
	private boolean enableStatementCache = true;
	@IsField
	private boolean enableTableSessionCache;
	@IsField
	private CreateMode createMode;
	@IsField
	private String sqlParameterPrefix="#{";
	@IsField
	private String sqlParameterSuffix="}";
	@IsField
	private String sqlParameterSplit=",";
	@IsField
	private DefaultValueHandler sqlParameterDefaultValueHandler;
	private SqlParameterConfigHolder sqlParameterConfigHolder; // 根据上面sql参数的配置信息, 最后创建出该实例
	
	public EnvironmentProperty(String id, Map<String, String> propertyMap, DialectKey key, MappingContainer mappingContainer) throws Exception {
		this.id = id;
		this.dialect = DialectContainer.get(key);
		this.mappingContainer = mappingContainer==null?new ApplicationMappingContainer():mappingContainer;
		
		List<String> fieldNames = getFieldNames(this.getClass().getDeclaredFields());
		setFieldValues(fieldNames, propertyMap);
		postProcessingFiledValues();
	}
	
	/**
	 * 获取属性名集合
	 * @param fields
	 * @return 
	 */
	private List<String> getFieldNames(Field[] fields) {
		List<String> fieldNames = new ArrayList<String>(fields.length);
		for (Field field : fields) {
			if(field.getAnnotation(IsField.class) != null) 
				fieldNames.add(field.getName());
		}
		return fieldNames;
	}

	/**
	 * 根据fieldName，调用对应的set方法
	 * @param fieldNames
	 * @param propertyMap
	 * @throws Exception
	 */
	private void setFieldValues(List<String> fieldNames, Map<String, String> propertyMap) throws Exception {
		boolean isEmpty = propertyMap.isEmpty();
		Class<?> clz = this.getClass();
		for (String fieldName : fieldNames) 
			clz.getDeclaredMethod("set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1), String.class).invoke(this, isEmpty?null:propertyMap.get(fieldName));
		
		if(!isEmpty)
			propertyMap.clear();
	}
	
	/**
	 * 后置处理属性值
	 */
	private void postProcessingFiledValues() {
		sqlParameterConfigHolder = new SqlParameterConfigHolder(sqlParameterPrefix, sqlParameterSuffix, sqlParameterSplit, sqlParameterDefaultValueHandler);
	}

	void setEnableStatementCache(String value) {
		if(DataTypeValidateUtil.isBoolean(value)) {
			this.enableStatementCache = Boolean.parseBoolean(value);
		}
	}
	void setEnableTableSessionCache(String value) {
		if(DataTypeValidateUtil.isBoolean(value)) {
			this.enableTableSessionCache = Boolean.parseBoolean(value);
		}
	}
	void setCreateMode(String value) {
		if(StringUtil.unEmpty(value)) 
			this.createMode = CreateMode.toValue(value);
	}
	void setSqlParameterPrefix(String value) {
		if(StringUtil.unEmpty(value))
			this.sqlParameterPrefix = value;
	}
	void setSqlParameterSuffix(String value) {
		if(StringUtil.unEmpty(value))
			this.sqlParameterSuffix = value;
	}
	void setSqlParameterSplit(String value) {
		if(StringUtil.unEmpty(value))
			this.sqlParameterSplit = value;
	}
	void setSqlParameterDefaultValueHandler(String value) {
		if(StringUtil.isEmpty(value))
			this.sqlParameterDefaultValueHandler = new DefaultValueHandler();
		else
			this.sqlParameterDefaultValueHandler = (DefaultValueHandler) ClassUtil.newInstance(value);
	}

	/**
	 * 获取id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 获取方言
	 */
	public Dialect getDialect() {
		return dialect;
	}
	
	/**
	 * 获取映射容器
	 */
	public MappingContainer getMappingContainer() {
		return mappingContainer;
	}
	
	/**
	 * 是否启用Statement缓存
	 */
	public boolean enableStatementCache() {
		return enableStatementCache;
	}
	
	/**
	 * 是否启用TableSession缓存
	 */
	public boolean enableTableSessionCache() {
		return enableTableSessionCache;
	}
	
	/**
	 * 获取全局的CreateMode
	 */
	public CreateMode getCreateMode() {
		return createMode;
	}

	/**
	 * 获取sql参数配置持有器
	 */
	public SqlParameterConfigHolder getSqlParameterConfigHolder() {
		return sqlParameterConfigHolder;
	}
}
