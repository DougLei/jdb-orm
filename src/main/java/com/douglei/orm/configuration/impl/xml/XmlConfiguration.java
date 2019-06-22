package com.douglei.orm.configuration.impl.xml;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.configuration.ConfigurationInitialException;
import com.douglei.orm.configuration.SelfCheckingException;
import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.configuration.environment.mapping.MappingWrapper;
import com.douglei.orm.configuration.environment.property.EnvironmentProperty;
import com.douglei.orm.configuration.extconfiguration.ExtConfiguration;
import com.douglei.orm.configuration.impl.xml.element.environment.XmlEnvironment;
import com.douglei.orm.configuration.impl.xml.element.extconfiguration.XmlExtConfiguration;
import com.douglei.orm.configuration.impl.xml.element.properties.Properties;
import com.douglei.orm.configuration.impl.xml.util.Dom4jElementUtil;
import com.douglei.orm.context.XmlReaderContext;
import com.douglei.orm.sessionfactory.SessionFactory;
import com.douglei.orm.sessionfactory.SessionFactoryImpl;
import com.douglei.tools.utils.CloseUtil;
import com.douglei.tools.utils.IdentityUtil;
import com.douglei.tools.utils.StringUtil;

/**
 * xml配置接口实现
 * @author DougLei
 */
public class XmlConfiguration implements Configuration {
	private static final Logger logger = LoggerFactory.getLogger(XmlConfiguration.class);
	private Document xmlDocument;
	private SessionFactory sessionFactory;
	
	/**
	 * <configuration>节点中的唯一标识
	 */
	private String id;
	/**
	 * 对应<properties>节点
	 */
	private Properties properties;
	/**
	 * 对应<ext-configuration>节点
	 */
	private ExtConfiguration extConfiguration;
	/**
	 * 对应<environment>节点
	 */
	private Environment environment;
	
	
	public XmlConfiguration() {
		this(DEFAULT_CONF_FILE);
	}
	public XmlConfiguration(String configurationFile) {
		this(XmlConfiguration.class.getClassLoader().getResourceAsStream(configurationFile));
	}
	public XmlConfiguration(InputStream in) {
		logger.debug("根据xml配置文件，初始化configuration实例");
		try {
			xmlDocument = XmlReaderContext.getConfigurationSAXReader().read(in);
			initXmlConfiguration();
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			CloseUtil.closeIO(in);
		}
	}
	
	/**
	 * 根据xml配置文件，初始化Configuration对象
	 */
	private void initXmlConfiguration() {
		try {
			logger.debug("开始初始化jdb-orm系统的配置信息");
			if(logger.isDebugEnabled()) {
				logger.debug("初始化的xml配置内容为: {}", xmlDocument.asXML());
			}
			Element root = xmlDocument.getRootElement();
			setId(root.attributeValue("id"));
			setProperties(new Properties(root.element("properties")));
			setExtConfiguration(new XmlExtConfiguration(root.element("ext-configuration")));
			setEnvironment(new XmlEnvironment(Dom4jElementUtil.validateElementExists("environment", root), properties, extConfiguration));
			logger.debug("结束初始化jdb-orm系统的配置信息");
		} catch (Exception e) {
			destroy();
			throw new ConfigurationInitialException("jdb-orm程序在初始化时出现异常", e);
		}
	}
	
	@Override
	public SessionFactory buildSessionFactory() {
		if(sessionFactory == null) {
			sessionFactory = new SessionFactoryImpl(this);
		}
		return sessionFactory;
	}
	
	@Override
	public void destroy() {
		logger.debug("{} 开始 destroy", getClass());
		if(properties != null) {
			properties.destroy();
		}
		if(extConfiguration != null) {
			extConfiguration.destroy();
		}
		if(environment != null) {
			environment.destroy();
		}
		logger.debug("{} 结束 destroy", getClass());
	}
	
	// -------------------------------------------------------------
	@Override
	public String getId() {
		return id;
	}
	@Override
	public DataSourceWrapper getDataSourceWrapper() {
		return environment.getDataSourceWrapper();
	}
	@Override
	public Environment getEnvironment() {
		return environment;
	}
	@Override
	public EnvironmentProperty getEnvironmentProperty() {
		return environment.getEnvironmentProperty();
	}
	@Override
	public MappingWrapper getMappingWrapper() {
		return environment.getMappingWrapper();
	}
	
	private void setId(String id) {
		if(StringUtil.isEmpty(id)) {
			id = IdentityUtil.getUUID();
		}
		this.id = id;
	}
	private void setProperties(Properties properties) {
		this.properties = properties;
	}
	private void setExtConfiguration(ExtConfiguration extConfiguration) {
		this.extConfiguration = extConfiguration;
	}
	private void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	@Override
	public void selfChecking() throws SelfCheckingException {
	}
}
