package com.douglei.orm.configuration.impl.element.environment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.configuration.ExternalDataSource;
import com.douglei.orm.configuration.environment.DatabaseMetadata;
import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.configuration.environment.mapping.MappingEntity;
import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.configuration.environment.mapping.container.MappingContainer;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.configuration.impl.element.environment.datasource.DataSourceWrapperImpl;
import com.douglei.orm.configuration.impl.element.environment.mapping.AddOrCoverMappingEntity;
import com.douglei.orm.configuration.impl.element.environment.property.EnvironmentPropertyImpl;
import com.douglei.orm.configuration.impl.element.properties.Properties;
import com.douglei.orm.configuration.impl.util.Dom4jElementUtil;
import com.douglei.orm.core.dialect.mapping.MappingHandler;
import com.douglei.tools.instances.scanner.FileScanner;
import com.douglei.tools.utils.StringUtil;

/**
 * <environment>节点
 * @author DougLei
 */
public class EnvironmentImpl implements Environment{
	private static final Logger logger = LoggerFactory.getLogger(EnvironmentImpl.class);
	private Properties properties;
	private DataSourceWrapper dataSourceWrapper;
	private EnvironmentProperty environmentProperty;
	private MappingHandler mappingHandler;
	
	public EnvironmentImpl(String id, Element environmentElement, Properties properties, ExternalDataSource exDataSource, MappingContainer mappingContainer) throws Exception {
		logger.debug("开始处理<environment>元素");
		this.properties = properties;
		setDataSourceWrapper(exDataSource==null?Dom4jElementUtil.validateElementExists("datasource", environmentElement):exDataSource);// 处理配置的数据源
		setEnvironmentProperties(id, Dom4jElementUtil.elements("property", environmentElement), mappingContainer);// 处理environment下的所有property元素
		addMapping(environmentElement.element("mappings"));// 处理配置的映射文件
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
		
		dataSourceWrapper = new DataSourceWrapperImpl((DataSource)dataSourceInstance, closeMethod, propertyMap);
		logger.debug("处理数据源结束");
	}
	
	/**
	 * 处理environment下的所有property元素
	 * @param id
	 * @param elements
	 * @param mappingContainer
	 * @throws SQLException 
	 */
	private void setEnvironmentProperties(String id, List<Element> elements, MappingContainer mappingContainer) throws SQLException {
		logger.debug("开始处理<environment>下的所有property元素");
		Map<String, String> propertyMap = elementListToPropertyMap(elements);
		
		DatabaseMetadata databaseMetadata = new DatabaseMetadata(dataSourceWrapper.getConnection(false, null).getConnection());
		EnvironmentPropertyImpl environmentProperty = new EnvironmentPropertyImpl(id, propertyMap, databaseMetadata, mappingContainer);
		if(environmentProperty.getDialect() == null) {
			if(logger.isDebugEnabled()) {
				logger.debug("<environment>没有配置dialect, 系统从DataSource中获取的DatabaseMetadata={}", databaseMetadata);
			}
			environmentProperty.setDialectByDatabaseMetadata(databaseMetadata);
		}
		
		this.environmentProperty = environmentProperty;
		EnvironmentContext.setEnvironmentProperty(environmentProperty);
		logger.debug("处理<environment>下的所有property元素结束");
	}
	
	/**
	 * 处理environment下的mappings元素, 扫描并添加映射
	 * @param element
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private void addMapping(Element element) throws Exception {
		logger.debug("开始处理<environment>下的<mappings>元素");
		this.mappingHandler = new MappingHandler(environmentProperty.getMappingContainer(), dataSourceWrapper, environmentProperty.getDialect().getTableSqlStatementHandler());
		
		if(environmentProperty.clearMappingContainerOnStart())
			environmentProperty.getMappingContainer().clear();
		
		if(element != null) {
			List<Attribute> paths = element.selectNodes("mapping/@path");
			
			if(!paths.isEmpty()) {
				StringBuilder path = new StringBuilder(paths.size() * 20);
				paths.forEach(p -> {
					path.append(",").append(p.getValue());
				});
				
				FileScanner fileScanner = new FileScanner(MappingType.getMappingFileSuffixs());
				List<String> list = fileScanner.multiScan("true".equalsIgnoreCase(element.attributeValue("searchAll")), path.substring(1).split(","));
				if(!list.isEmpty()) {
					List<MappingEntity> mappingEntities = new ArrayList<MappingEntity>(list.size());
					for (String file : list) 
						mappingEntities.add(new AddOrCoverMappingEntity(file));
					this.mappingHandler.execute(mappingEntities);
				}
			}
		}
		logger.debug("处理<environment>下的<mappings>元素结束");
	}
	
	@Override
	public void destroy() throws DestroyException{
		if(logger.isDebugEnabled()) logger.debug("{} 开始 destroy", getClass().getName());
		if(dataSourceWrapper != null) {
			dataSourceWrapper.destroy();
		}
		if(environmentProperty != null && environmentProperty.getMappingContainer() != null) {
			environmentProperty.getMappingContainer().destroy();
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
	public MappingHandler getMappingHandler() {
		return mappingHandler;
	}
}
