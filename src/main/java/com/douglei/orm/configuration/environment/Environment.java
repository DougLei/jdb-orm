package com.douglei.orm.configuration.environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.Dom4jUtil;
import com.douglei.orm.configuration.ExternalDataSource;
import com.douglei.orm.configuration.OrmException;
import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.configuration.environment.mapping.MappingConfiguration;
import com.douglei.orm.configuration.environment.mapping.SqlMappingConfiguration;
import com.douglei.orm.configuration.environment.mapping.SqlMappingParameterDefaultValueHandler;
import com.douglei.orm.dialect.Dialect;
import com.douglei.orm.dialect.DialectContainer;
import com.douglei.orm.mapping.MappingContainer;
import com.douglei.orm.mapping.MappingContainerImpl;
import com.douglei.orm.mapping.MappingType;
import com.douglei.orm.mapping.MappingTypeContainer;
import com.douglei.orm.mapping.handler.MappingHandler;
import com.douglei.orm.mapping.handler.entity.AddOrCoverMappingEntity;
import com.douglei.orm.mapping.handler.entity.MappingEntity;
import com.douglei.tools.StringUtil;
import com.douglei.tools.file.scanner.impl.ResourceScanner;
import com.douglei.tools.reflect.ClassUtil;
import com.douglei.tools.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class Environment {
	private static final Logger logger = LoggerFactory.getLogger(Environment.class);
	private EnvironmentProperty property;
	private DataSourceWrapper dataSource;
	private Dialect dialect;
	private MappingHandler mappingHandler;
	
	@SuppressWarnings("unchecked")
	public Environment(Element environmentElement, Properties properties, ExternalDataSource exDataSource, MappingContainer mappingContainer) throws Exception {
		logger.debug("开始处理<environment>");
		
		// 设置environment下的所有property
		setProperties(environmentElement.elements("property"), properties);
		
		// 设置数据源
		setDataSource(exDataSource==null?Dom4jUtil.getElement("datasource", environmentElement):exDataSource, properties);
		
		// 设置方言
		this.dialect = DialectContainer.get(dataSource.getConnection(false, null).getConnection());
		
		// 设置MappingHandler
		setMappingHandler(environmentElement.element("mapping"), mappingContainer);
		
		logger.debug("结束处理<environment>");
	}
	
	
	// 将<property>集合, 转换成map
	private Map<String, String> propertyElementListToPropertyMap(List<Element> propertyElementList, Properties properties){
		if(propertyElementList.isEmpty())
			return null;
		
		Map<String, String> propertyMap = new HashMap<String, String>();
		for (Element elem : propertyElementList) 
			propertyMap.put(elem.attributeValue("name"), getValue(elem.attributeValue("value"), properties));
		return propertyMap;
	}
	private String getValue(String kv, Properties properties) {
		String value = properties.getValue(kv);
		if(value == null) 
			return kv;
		return value;
	}
	
	
	// 设置environment下的所有property
	private void setProperties(List<Element> propertyElementList, Properties properties) throws Exception {
		logger.debug("开始处理<environment>下的<property>");
		this.property = new EnvironmentProperty(propertyElementListToPropertyMap(propertyElementList, properties));
		logger.debug("结束处理<environment>下的<property>");
	}
	
	
	// 设置数据源
	@SuppressWarnings("unchecked")
	private void setDataSource(Object object, Properties properties) {
		logger.debug("开始设置数据源");
		
		if(object instanceof ExternalDataSource) {
			logger.debug("start: 使用外部的数据源");
			this.dataSource = new DataSourceWrapper(((ExternalDataSource)object).getDataSource(), ((ExternalDataSource)object).getCloseMethodName());
			logger.debug("end: 使用外部的数据源");
		}else {
			logger.debug("start: 使用<datasource>数据源");
			
			String clazz = ((Element) object).attributeValue("class");
			if(StringUtil.isEmpty(clazz)) 
				throw new OrmException("<datasource>的class属性值不能为空");
			
			Object datasource= ClassUtil.newInstance(clazz);
			if(!(datasource instanceof DataSource)) 
				throw new OrmException("<datasource>的class属性值, 必须实现["+DataSource.class.getName()+"]接口");
			
			Map<String, String> propertyMap = propertyElementListToPropertyMap(((Element) object).elements("property"), properties);
			if(propertyMap==null) 
				throw new OrmException("<datasource>中必须配置必要的数据库连接参数");
			IntrospectorUtil.setValues(propertyMap, datasource);
			
			this.dataSource = new DataSourceWrapper((DataSource)datasource, ((Element) object).attributeValue("closeMethod"));
			logger.debug("end: 使用<datasource>数据源");
		}
		logger.debug("处理数据源结束");
	}
	
	
	// 设置MappingHandler
	@SuppressWarnings("unchecked")
	private void setMappingHandler(Element mappingElement, MappingContainer mappingContainer) {
		logger.debug("开始处理<environment>下的<mapping>");
		
		// 解析映射配置
		MappingConfiguration configuration = parseMappingConfiguration(mappingElement);
		
		// 注册映射类型
		registerMappingType(mappingElement.elements("register"));
		
		// 创建MappingHandler; 创建容器或清空容器
		if(mappingContainer == null)
			mappingContainer = new MappingContainerImpl();
		else
			mappingContainer.clear();
		this.mappingHandler = new MappingHandler(configuration, mappingContainer, dataSource);
		
		// 扫描配置的映射
		scanMappings(mappingElement.elements("scanner"));
		
		logger.debug("结束处理<environment>下的<mapping>");
	}
	
	// 解析映射配置
	private MappingConfiguration parseMappingConfiguration(Element mappingElement) {
		MappingConfiguration configuration = new MappingConfiguration();
		
		// 设置内置的sql映射配置
		Element sqlMappingElement = mappingElement.element("sql");
		configuration.setSqlMappingConfiguration(new SqlMappingConfiguration(
				sqlMappingElement.attributeValue("parameterPrefix", "#{"), 
				sqlMappingElement.attributeValue("parameterSuffix", "}"), 
				sqlMappingElement.attributeValue("parameterSplit", ","), 
				(SqlMappingParameterDefaultValueHandler)ClassUtil.newInstance(sqlMappingElement.attributeValue("parameterDefaultValueHandler", SqlMappingParameterDefaultValueHandler.class.getName()))));
		
		logger.debug("解析出的映射配置信息: {}", configuration);
		return configuration;
	}
	
	// 注册配置的映射类型
	private void registerMappingType(List<Element> registerElements) {
		if(registerElements.size() > 0) {
			for (Element elem : registerElements) 
				MappingTypeContainer.register((MappingType)ClassUtil.newInstance(elem.attributeValue("class")));
		}
	}

	// 扫描配置的映射
	private void scanMappings(List<Element> scannerElements) {
		if(scannerElements.isEmpty())
			return;
		
		EnvironmentContext.setEnvironment(this);
		
		List<MappingEntity> entities = new ArrayList<MappingEntity>();
		ResourceScanner scanner = new ResourceScanner(MappingTypeContainer.getFileSuffixes());
		for (Element elem : scannerElements) {
			for(String file : scanner.scan("true".equalsIgnoreCase(elem.attributeValue("scanAll")), elem.attributeValue("path"))) 
				entities.add(new AddOrCoverMappingEntity(file));
		}
			
		if(entities.size() > 0) 
			this.mappingHandler.execute(entities);
	}
	
	/**
	 * 销毁Environment
	 * @throws Exception 
	 */
	public void destroy() throws Exception {
		logger.debug("开始销毁[com.douglei.orm.configuration.environment.Environment]实例");
		if(dataSource != null) {
			dataSource.close();
			dataSource = null;
		}
		if(mappingHandler != null) {
			mappingHandler.uninstall();
			mappingHandler = null;
		}
		logger.debug("结束销毁[com.douglei.orm.configuration.environment.Environment]实例");
	}
	
	
	/**
	 * 获取环境属性
	 * @return
	 */
	public EnvironmentProperty getProperty() {
		return property;
	}
	/**
	 * 获取数据源
	 * @return
	 */
	public DataSourceWrapper getDataSource() {
		return dataSource;
	}
	/**
	 * 获取方言
	 * @return
	 */
	public Dialect getDialect() {
		return dialect;
	}
	/**
	 * 获取映射处理器
	 * @return
	 */
	public MappingHandler getMappingHandler() {
		return mappingHandler;
	}
}
