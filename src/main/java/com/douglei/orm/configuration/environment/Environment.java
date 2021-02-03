package com.douglei.orm.configuration.environment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.Dom4jUtil;
import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.configuration.ExternalDataSource;
import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.configuration.properties.Properties;
import com.douglei.orm.dialect.DialectKey;
import com.douglei.orm.mapping.container.MappingContainer;
import com.douglei.orm.mapping.handler.MappingHandler;
import com.douglei.orm.mapping.handler.entity.MappingEntity;
import com.douglei.orm.mapping.handler.entity.impl.AddOrCoverMappingEntity;
import com.douglei.orm.metadata.type.MetadataTypeContainer;
import com.douglei.tools.StringUtil;
import com.douglei.tools.file.scanner.impl.ResourceScanner;
import com.douglei.tools.reflect.ClassUtil;

/**
 * 
 * @author DougLei
 */
public class Environment {
	private static final Logger logger = LoggerFactory.getLogger(Environment.class);
	private Properties properties;
	private DataSourceWrapper dataSourceWrapper;
	private EnvironmentProperty environmentProperty;
	private MappingHandler mappingHandler;
	
	@SuppressWarnings("unchecked")
	public Environment(String id, Element environmentElement, Properties properties, ExternalDataSource exDataSource, MappingContainer mappingContainer) throws Exception {
		logger.debug("开始处理<environment>元素");
		this.properties = properties;
		setDataSourceWrapper(exDataSource==null?Dom4jUtil.getElement("datasource", environmentElement):exDataSource);// 处理配置的数据源
		setEnvironmentProperties(id, environmentElement.elements("property"), mappingContainer);// 处理environment下的所有property元素
		addMapping(environmentElement.element("mappings"));// 处理配置的映射文件
		logger.debug("处理<environment>元素结束");
	}
	
	/**
	 * 将elements的键值对, 组成map
	 * @param elements
	 * @return
	 */
	private Map<String, String> elementListToPropertyMap(List<Element> elements){
		if(elements.isEmpty())
			return Collections.emptyMap();
		
		Map<String, String> propertyMap = new HashMap<String, String>();
		for (Element elem : elements) 
			propertyMap.put(elem.attributeValue("name"), getValue(elem.attributeValue("value"), properties));
		return propertyMap;
	}
	private String getValue(String key, Properties properties) {
		String value = properties.getValue(key);
		if(value == null) 
			return key;
		return value;
	}
	
	/**
	 * 处理environment下的datasource元素
	 * @param object
	 */
	@SuppressWarnings("unchecked")
	private void setDataSourceWrapper(Object object) {
		logger.debug("开始处理数据源");
		Object datasource = null;
		String closeMethod = null;
		Map<String, String> propertyMap = null;
		
		if(object instanceof ExternalDataSource) {
			logger.debug("开始处理外部传入的数据源");
			datasource = ((ExternalDataSource)object).getDataSource();
			closeMethod = ((ExternalDataSource)object).getCloseMethodName();
			logger.debug("处理外部传入的数据源结束");
		}else {
			logger.debug("开始处理<environment>下的<datasource>元素");
			Element element = (Element) object;
			String clazz = element.attributeValue("class");
			if(StringUtil.isEmpty(clazz)) 
				throw new NullPointerException("<datasource>元素的class属性不能为空");
			
			datasource = ClassUtil.newInstance(clazz);
			if(!(datasource instanceof DataSource)) 
				throw new ClassCastException("<datasource>元素的class, 必须实现 "+DataSource.class.getName()+" 接口");
			
			closeMethod = element.attributeValue("closeMethod");
			
			propertyMap = elementListToPropertyMap(element.elements("property"));
			if(propertyMap.isEmpty()) 
				throw new NullPointerException("<datasource>元素下，必须配置必要的数据库连接参数");
			
			logger.debug("处理<environment>下的<datasource>元素结束");
		}
		
		dataSourceWrapper = new DataSourceWrapper((DataSource)datasource, closeMethod, propertyMap);
		logger.debug("处理数据源结束");
	}
	
	
	
	
	/**
	 * 处理environment下的所有property元素
	 * @param id
	 * @param elements
	 * @param mappingContainer
	 * @throws Exception
	 */
	private void setEnvironmentProperties(String id, List<Element> elements, MappingContainer mappingContainer) throws Exception {
		logger.debug("开始处理<environment>下的所有property元素");
		Map<String, String> propertyMap = elementListToPropertyMap(elements);
		DialectKey key = new DialectKey(dataSourceWrapper.getConnection(false, null).getConnection());
		this.environmentProperty = new EnvironmentProperty(id, propertyMap, key, mappingContainer);
		EnvironmentContext.setProperty(environmentProperty);
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
		this.mappingHandler = new MappingHandler(environmentProperty.getMappingContainer(), dataSourceWrapper);
		
		environmentProperty.getMappingContainer().clear();
		
		if(element != null) {
			List<Attribute> paths = element.selectNodes("mapping/@path");
			
			if(!paths.isEmpty()) {
				StringBuilder path = new StringBuilder(paths.size() * 20);
				paths.forEach(p -> {
					path.append(",").append(p.getValue());
				});
				
				List<String> list = new ResourceScanner(MetadataTypeContainer.getFileSuffixes().toArray(new String[MetadataTypeContainer.getFileSuffixes().size()])).multiScan("true".equalsIgnoreCase(element.attributeValue("scanAll")), path.substring(1).split(","));
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

	/**
	 * 销毁
	 * @throws Exception 
	 */
	public void destroy() throws Exception {
		if(logger.isDebugEnabled()) 
			logger.debug("{} 开始 destroy", getClass().getName());
		if(dataSourceWrapper != null) 
			dataSourceWrapper.close();
		if(environmentProperty != null && environmentProperty.getMappingContainer() != null) 
			environmentProperty.getMappingContainer().clear();
		if(logger.isDebugEnabled()) 
			logger.debug("{} 结束 destroy", getClass().getName());
	}
	
	// -------------------------------------------------------------
	/**
	 * 获取数据源包装类
	 * @return
	 */
	public EnvironmentProperty getEnvironmentProperty() {
		return environmentProperty;
	}
	
	/**
	 * 获取环境属性实例
	 * @return
	 */
	public DataSourceWrapper getDataSourceWrapper() {
		return dataSourceWrapper;
	}
	
	/**
	 * 获取映射处理器
	 * @return
	 */
	public MappingHandler getMappingHandler() {
		return mappingHandler;
	}
}
