package com.douglei.orm.configuration.impl.xml.element.environment;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.ExternalDataSource;
import com.douglei.orm.configuration.SelfCheckingException;
import com.douglei.orm.configuration.environment.DatabaseMetadata;
import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.configuration.environment.mapping.MappingWrapper;
import com.douglei.orm.configuration.environment.mapping.store.MappingStore;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.configuration.environment.remote.database.RemoteDatabase;
import com.douglei.orm.configuration.ext.configuration.ExtConfiguration;
import com.douglei.orm.configuration.impl.xml.element.environment.datasource.XmlDataSourceWrapper;
import com.douglei.orm.configuration.impl.xml.element.environment.mapping.XmlMappingWrapper;
import com.douglei.orm.configuration.impl.xml.element.environment.property.XmlEnvironmentProperty;
import com.douglei.orm.configuration.impl.xml.element.environment.remote.database.XmlRemoteDatabase;
import com.douglei.orm.configuration.impl.xml.element.properties.Properties;
import com.douglei.orm.configuration.impl.xml.util.Dom4jElementUtil;
import com.douglei.tools.utils.StringUtil;

/**
 * <environment>节点
 * @author DougLei
 */
public class XmlEnvironment implements Environment{
	private static final Logger logger = LoggerFactory.getLogger(XmlEnvironment.class);
	private Properties properties;
	
	private EnvironmentProperty environmentProperty;
	
	private RemoteDatabase remoteDatabase;
	
	private DataSourceWrapper dataSourceWrapper;
	
	private MappingWrapper mappingWrapper;
	
	public XmlEnvironment() {
	}
	public XmlEnvironment(String id, Element environmentElement, Properties properties, ExternalDataSource dataSource, MappingStore mappingStore, ExtConfiguration extConfiguration) throws Exception {
		logger.debug("开始处理<environment>元素");
		this.properties = properties;
		
		setRemoteDatabase(environmentElement.element("remoteDatabase"));// 处理远程数据库
		
		setDataSourceWrapper(dataSource==null?Dom4jElementUtil.validateElementExists("datasource", environmentElement):dataSource);// 处理配置的数据源
		
		setEnvironmentProperties(id, Dom4jElementUtil.elements("property", environmentElement), mappingStore, extConfiguration);// 处理environment下的所有property元素
		
		setMappingWrapper(environmentElement.element("mappings"));// 处理配置的映射文件
		
		logger.debug("处理<environment>元素结束");
	}
	
	private Map<String, String> elementListToPropertyMap(List<Element> elements){
		if(elements != null) {
			Map<String, String> propertyMap = new HashMap<String, String>(elements.size());
			for (Element elem : elements) {
				propertyMap.put(elem.attributeValue("name"), getValue(elem.attributeValue("value"), properties));
			}
			return propertyMap;
		}
		return null;
	}
	private String getValue(String key, Properties properties) {
		String propertyValue = properties.getProperties(key);
		if(propertyValue == null) {
			return key;
		}
		return propertyValue;
	}
	
	private void setRemoteDatabase(Element remoteDatabaseElement) throws SQLException {
		if(remoteDatabaseElement != null) {
			logger.debug("开始处理<environment>下的<remoteDatabase>元素");
			Map<String, String> propertyMap = elementListToPropertyMap(Dom4jElementUtil.elements("property", remoteDatabaseElement));
			remoteDatabase = new XmlRemoteDatabase(propertyMap, Dom4jElementUtil.validateElementExists("createSql", remoteDatabaseElement), Dom4jElementUtil.validateElementExists("dropSql", remoteDatabaseElement));
			remoteDatabase.selfChecking();
			remoteDatabase.executeCreate();
			logger.debug("处理<environment>下的<remoteDatabase>元素结束");
		}
	}
	
	/**
	 * 处理environment下的datasource元素
	 * @param dsobj
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private void setDataSourceWrapper(Object dsobj) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		logger.debug("开始处理数据源");
		Object dataSourceInstance = null;
		String closeMethod = null;
		Map<String, String> propertyMap = null;
		
		if(dsobj instanceof ExternalDataSource) {
			logger.debug("开始处理外部传入的数据源");
			dataSourceInstance = ((ExternalDataSource)dsobj).getDataSource();
			closeMethod = ((ExternalDataSource)dsobj).getCloseMethodName();
			logger.debug("处理外部传入的数据源结束");
		}else {
			logger.debug("开始处理<environment>下的<datasource>元素");
			Element datasourceElement = (Element) dsobj;
			String dataSourceClassStr = datasourceElement.attributeValue("class");
			if(StringUtil.isEmpty(dataSourceClassStr)) {
				throw new NullPointerException("<datasource>元素的class属性不能为空");
			}
			dataSourceInstance = Class.forName(dataSourceClassStr).newInstance();
			if(!(dataSourceInstance instanceof DataSource)) {
				throw new ClassCastException("<datasource>元素的class, 必须实现了"+DataSource.class.getName()+"接口");
			}
			
			closeMethod = datasourceElement.attributeValue("closeMethod");
			
			propertyMap = elementListToPropertyMap(Dom4jElementUtil.elements("property", datasourceElement));
			if(propertyMap == null || propertyMap.size() == 0) {
				throw new NullPointerException("<datasource>元素下，必须配置必要的数据库连接参数");
			}
			
			logger.debug("处理<environment>下的<datasource>元素结束");
		}
		
		dataSourceWrapper = new XmlDataSourceWrapper((DataSource)dataSourceInstance, closeMethod, propertyMap);
		logger.debug("处理数据源结束");
	}
	
	/**
	 * 处理environment下的所有property元素
	 * @param id
	 * @param elements
	 * @param mappingStore
	 * @param extConfiguration
	 * @throws SQLException 
	 */
	private void setEnvironmentProperties(String id, List<Element> elements, MappingStore mappingStore, ExtConfiguration extConfiguration) throws SQLException {
		logger.debug("开始处理<environment>下的所有property元素");
		Map<String, String> propertyMap = elementListToPropertyMap(elements);
		
		DatabaseMetadata databaseMetadata = new DatabaseMetadata(dataSourceWrapper.getConnection(false, null).getConnection());
		XmlEnvironmentProperty xmlEnvironmentProperty = new XmlEnvironmentProperty(id, propertyMap, databaseMetadata, mappingStore);
		if(xmlEnvironmentProperty.getDialect() == null) {
			if(logger.isDebugEnabled()) {
				logger.debug("<environment>没有配置dialect, 系统从DataSource中获取的DatabaseMetadata={}", databaseMetadata);
			}
			xmlEnvironmentProperty.setDialectByDatabaseMetadata(databaseMetadata);
		}
		
		xmlEnvironmentProperty.setExtDataTypeHandlers(extConfiguration.getExtDataTypeHandlerList());
		
		this.environmentProperty = xmlEnvironmentProperty;
		logger.debug("处理<environment>下的所有property元素结束");
	}
	
	/**
	 * 处理environment下的mappings元素
	 * @param element
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private void setMappingWrapper(Element element) throws Exception {
		logger.debug("开始处理<environment>下的<mappings>元素");
		if(element != null) {
			mappingWrapper = new XmlMappingWrapper("true".equalsIgnoreCase(element.attributeValue("searchAll")), element.selectNodes("mapping/@path"), dataSourceWrapper, environmentProperty);
		}else {
			mappingWrapper = new XmlMappingWrapper(environmentProperty);
		}
		logger.debug("处理<environment>下的<mappings>元素结束");
	}
	
	@Override
	public void destroy() throws DestroyException{
		if(logger.isDebugEnabled()) logger.debug("{} 开始 destroy", getClass().getName());
		if(remoteDatabase != null) {
			remoteDatabase.destroy();
		}
		if(dataSourceWrapper != null) {
			dataSourceWrapper.destroy();
		}
		if(mappingWrapper != null) {
			mappingWrapper.destroy();
		}
		if(logger.isDebugEnabled()) logger.debug("{} 结束 destroy", getClass().getName());
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
