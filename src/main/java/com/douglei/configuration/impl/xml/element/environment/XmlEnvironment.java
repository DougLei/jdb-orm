package com.douglei.configuration.impl.xml.element.environment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.LocalConfigurationDialectHolder;
import com.douglei.configuration.DestroyException;
import com.douglei.configuration.SelfCheckingException;
import com.douglei.configuration.environment.Environment;
import com.douglei.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.configuration.extconfiguration.ExtConfiguration;
import com.douglei.configuration.impl.xml.element.environment.datasource.XmlDataSourceWrapper;
import com.douglei.configuration.impl.xml.element.environment.mapping.RepeatMappingsElementException;
import com.douglei.configuration.impl.xml.element.environment.mapping.XmlMappingWrapper;
import com.douglei.configuration.impl.xml.element.environment.property.XmlEnvironmentProperty;
import com.douglei.configuration.impl.xml.element.properties.Properties;
import com.douglei.configuration.impl.xml.util.ElementUtil;
import com.douglei.utils.StringUtil;

/**
 * <environment>节点
 * @author DougLei
 */
public class XmlEnvironment implements Environment{
	private static final Logger logger = LoggerFactory.getLogger(XmlEnvironment.class);
	private Properties properties;
	private ExtConfiguration extConfiguration;
	
	private EnvironmentProperty environmentProperty;
	
	private DataSourceWrapper dataSourceWrapper;
	
	private MappingWrapper mappingWrapper;
	
	public XmlEnvironment() {
	}
	public XmlEnvironment(List<?> environmentElements, Properties properties, ExtConfiguration extConfiguration) throws Exception {
		logger.debug("开始处理<environment>元素");
		Element element = ElementUtil.getNecessaryAndSingleElement("<environment>", environmentElements);
		this.properties = properties;
		this.extConfiguration = extConfiguration;
		
		setDataSourceWrapper(element.elements("datasource"));// 处理配置的数据源
		
		setEnvironmentProperties(element.elements("property"));// 处理environment下的所有property元素
		
		setMappingWrapper(element.elements("mappings"));// 处理配置的映射文件
		
		logger.debug("处理<environment>元素结束");
	}
	
	private Map<String, String> elementListToPropertyMap(List<?> elements){
		if(elements != null && elements.size() > 0) {
			Map<String, String> propertyMap = new HashMap<String, String>(elements.size());
			Element elem = null;
			for (Object object : elements) {
				elem = (Element) object;
				propertyMap.put(elem.attributeValue("name"), getValue(elem.attributeValue("value"), properties));
			}
			return propertyMap;
		}
		return null;
	}
	private String getValue(String key, Properties properties) {
		String propertyValue = properties.getProperties(key);
		if(propertyValue == null) {
			propertyValue = key;
		}
		return propertyValue;
	}
	
	/**
	 * 处理environment下的datasource元素
	 * @param elements
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private void setDataSourceWrapper(List<?> elements) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		logger.debug("开始处理<environment>下的<datasource>元素");
		Element datasourceElement = ElementUtil.getNecessaryAndSingleElement("<environment>下的<datasource>", elements);
		
		String dataSourceClassStr = datasourceElement.attributeValue("class");
		if(StringUtil.isEmpty(dataSourceClassStr)) {
			throw new NullPointerException("<datasource>元素的class属性不能为空");
		}
		Object dataSourceInstance = Class.forName(dataSourceClassStr).newInstance();
		if(!(dataSourceInstance instanceof DataSource)) {
			throw new ClassCastException("<datasource>元素的class, 必须实现了javax.sql.DataSource接口");
		}
		
		Map<String, String> propertyMap = elementListToPropertyMap(datasourceElement.elements("property"));
		if(propertyMap == null || propertyMap.size() == 0) {
			throw new NullPointerException("<datasource>元素下，必须配置必要的数据库连接参数");
		}
		
		dataSourceWrapper = new XmlDataSourceWrapper((DataSource)dataSourceInstance, datasourceElement.attributeValue("closeMethod"), propertyMap, this);
		logger.debug("处理<environment>下的<datasource>元素结束");
	}
	
	/**
	 * 处理environment下的所有property元素
	 * @param elements
	 * @throws SQLException 
	 */
	private void setEnvironmentProperties(List<?> elements) throws SQLException {
		logger.debug("开始处理<environment>下的所有property元素");
		Map<String, String> propertyMap = elementListToPropertyMap(elements);
		XmlEnvironmentProperty xmlEnvironmentProperty = new XmlEnvironmentProperty(propertyMap);
		
		if(xmlEnvironmentProperty.getDialect() == null) {
			Connection connection = dataSourceWrapper.getConnection(false, null).getConnection();
			String JDBCUrl = connection.getMetaData().getURL();
			connection.close();
			if(logger.isDebugEnabled()) {
				logger.debug("<environment>没有配置dialect, 系统从DataSource中获取的JDBCUrl={}", JDBCUrl);
			}
			xmlEnvironmentProperty.setDialectByJDBCUrl(JDBCUrl);
		}
		xmlEnvironmentProperty.setExtDataTypeHandlers(extConfiguration.getExtDataTypeHandlerList());
		
		this.environmentProperty = xmlEnvironmentProperty;
		logger.debug("处理<environment>下的所有property元素结束");
	}
	
	/**
	 * 处理environment下的mappings元素
	 * @param elements
	 * @throws Exception 
	 */
	private void setMappingWrapper(List<?> elements) throws Exception {
		logger.debug("开始处理<environment>下的<mappings>元素");
		if(elements != null && elements.size() > 0) {
			if(elements.size() > 1) {
				throw new RepeatMappingsElementException("<environment>下的<mappings>元素最多只能配置一个");
			}
			LocalConfigurationDialectHolder.setDialect(environmentProperty.getDialect());
			
			Element elem = ((Element) elements.get(0));
			mappingWrapper = new XmlMappingWrapper(elem.elements("mapping-scan"), elem.elements("mapping"), environmentProperty.getMappingStore());
		}else {
			mappingWrapper = new XmlMappingWrapper(environmentProperty.getMappingStore());
		}
		logger.debug("处理<environment>下的<mappings>元素结束");
	}
	
	@Override
	public void doDestroy() throws DestroyException{
		logger.debug("{} 开始 destroy", getClass());
		if(dataSourceWrapper != null) {
			dataSourceWrapper.doDestroy();
		}
		if(mappingWrapper != null) {
			mappingWrapper.doDestroy();
		}
		logger.debug("{} 结束 destroy", getClass());
	}
	
	// -------------------------------------------------------------
	@Override
	public EnvironmentProperty getEnvironmentProperty() {
		return environmentProperty;
	}
	@Override
	public DataSourceWrapper getDataSourceWrapper() {
		return dataSourceWrapper;
	}
	@Override
	public MappingWrapper getMappingWrapper() {
		return mappingWrapper;
	}

	@Override
	public void selfChecking() throws SelfCheckingException {
	}
}
