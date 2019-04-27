package com.douglei.configuration.impl.xml.element.environment.property;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.configuration.environment.property.FieldMetaData;
import com.douglei.configuration.impl.xml.element.environment.ReflectInvokeMethodException;
import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.DialectMapping;
import com.douglei.database.dialect.TransactionIsolationLevel;
import com.douglei.utils.StringUtil;
import com.douglei.utils.datatype.ValidationUtil;

/**
 * <environment>节点下所有的<property>节点
 * @author DougLei
 */
public class XmlEnvironmentProperty implements EnvironmentProperty{
	private static final Logger logger = LoggerFactory.getLogger(XmlEnvironmentProperty.class);
	
	private Map<String, String> propertyMap;
	private boolean propertyMapIsEmpty;
	
	
	@FieldMetaData(isRequired=true, isnullOfErrorMessage="<environment>节点中, 必须配置<property name=\"dialect\" value=\"xxx\">的元素, 用来指定dialect")
	private Dialect dialect;
	
	@FieldMetaData
	private TransactionIsolationLevel transactionIsolationLevel;
	
	@FieldMetaData
	private boolean enableSessionCache = true;
	
	public XmlEnvironmentProperty() {
	}
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
				logger.debug("SelfChecking EnvironmentProperty.{} Field", fieldNames.get(fieldNameIndex));
				
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
		if(propertyMap != null) {
			Class<?> clz = getClass();
			String value = null;
			for (String fieldName : fieldNames) {
				logger.debug("invoke EnvironmentProperty.{} Field's setXXX method", fieldName);
				
				value = propertyMapIsEmpty?null:propertyMap.get(fieldName);
				if(StringUtil.notEmpty(value)){
					try {
						clz.getDeclaredMethod(fieldNameToSetMethodName(fieldName), String.class).invoke(this, value);
					} catch (Exception e) {
						throw new ReflectInvokeMethodException("反射调用 class=["+clz.toString()+"], methodName=["+fieldNameToSetMethodName(fieldName)+"] 时出现异常", e);
					}
				}
			}
		}
	}
	private String fieldNameToSetMethodName(String fieldName) {
		return "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
	}

	
	void setDialect(String value) {
		if(logger.isDebugEnabled()) {
			logger.debug("{}.setDialect(), parameter value is {}", getClass(), value);
		}
		this.dialect = DialectMapping.getDialect(value);
	}
	void setTransactionIsolationLevel(String value) {
		if(logger.isDebugEnabled()) {
			logger.debug("{}.setTransactionIsolationLevel(), parameter value is {}", getClass(), value);
		}
		this.transactionIsolationLevel = TransactionIsolationLevel.toValue(value);
	}
	void setEnableSessionCache(String value) {
		if(logger.isDebugEnabled()) {
			logger.debug("{}.setEnableSessionCache(), parameter value is {}", getClass(), value);
		}
		if(ValidationUtil.isBoolean(value)) {
			this.enableSessionCache = Boolean.parseBoolean(value);
		}
	}
	
	@Override
	public Dialect getDialect() {
		return dialect;
	}
	@Override
	public TransactionIsolationLevel getTransactionIsolationLevel() {
		if(transactionIsolationLevel == null) {
			transactionIsolationLevel = dialect.getDefaultTransactionIsolationLevel();
			logger.debug("使用dialect=[{}] 默认的事物隔离级别: {}", dialect.getClass(),transactionIsolationLevel);
		}
		return transactionIsolationLevel;
	}
	@Override
	public boolean getEnableSessionCache() {
		return enableSessionCache;
	}
}
