package com.douglei.configuration.impl.xml;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.Configuration;
import com.douglei.configuration.ConfigurationInitialException;
import com.douglei.configuration.SelfCheckingException;
import com.douglei.configuration.environment.Environment;
import com.douglei.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.configuration.extconfiguration.ExtConfiguration;
import com.douglei.configuration.impl.xml.element.environment.XmlEnvironment;
import com.douglei.configuration.impl.xml.element.extconfiguration.XmlExtConfiguration;
import com.douglei.configuration.impl.xml.element.properties.Properties;
import com.douglei.configuration.impl.xml.util.Dom4jElementUtil;
import com.douglei.context.XmlReaderContext;
import com.douglei.sessionfactory.SessionFactory;
import com.douglei.sessionfactory.SessionFactoryImpl;
import com.douglei.utils.CloseUtil;
import com.douglei.utils.IdentityUtil;
import com.douglei.utils.StringUtil;

/**
 * xml配置接口实现
 * @author DougLei
 */
public class XmlConfiguration implements Configuration {
	private static final Logger logger = LoggerFactory.getLogger(XmlConfiguration.class);
	private static final String DEFAULT_CONF_FILE_PATH = "jdb-orm.conf.xml";
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
		this(DEFAULT_CONF_FILE_PATH);
	}
	public XmlConfiguration(String configurationFilePath) {
		this(XmlConfiguration.class.getClassLoader().getResourceAsStream(configurationFilePath));
	}
	public XmlConfiguration(InputStream in) {
		logger.info("根据xml配置文件，初始化configuration实例");
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
			id = IdentityUtil.get32UUID();
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
