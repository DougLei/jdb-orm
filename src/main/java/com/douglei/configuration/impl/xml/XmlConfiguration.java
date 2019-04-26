package com.douglei.configuration.impl.xml;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.Configuration;
import com.douglei.configuration.ConfigurationInitialException;
import com.douglei.configuration.SelfCheckingException;
import com.douglei.configuration.environment.Environment;
import com.douglei.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.configuration.environment.mapping.MappingWrapper;
import com.douglei.configuration.environment.property.EnvironmentProperty;
import com.douglei.configuration.impl.xml.element.environment.XmlEnvironment;
import com.douglei.configuration.impl.xml.element.properties.Properties;
import com.douglei.sessionfactory.SessionFactory;
import com.douglei.sessionfactory.SessionFactoryImpl;
import com.douglei.utils.CloseUtil;
import com.douglei.utils.ExceptionUtil;
import com.douglei.utils.IdentityUtil;
import com.douglei.utils.StringUtil;

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
	 * 对应<environment>节点
	 */
	private Environment environment;
	
	
	public XmlConfiguration() {
		this(DEFAULT_CONF_FILE_PATH);
	}
	public XmlConfiguration(String confFilePath) {
		this(XmlConfiguration.class.getClassLoader().getResourceAsStream(confFilePath));
	}
	public XmlConfiguration(InputStream in) {
		logger.info("根据xml配置文件，初始化configuration实例");
		try {
			xmlDocument = new SAXReader().read(in);
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
			setProperties(new Properties(root.elements("properties")));
			setEnvironment(new XmlEnvironment(root.elements("environment"), properties));
			logger.debug("结束初始化jdb-orm系统的配置信息");
		} catch (Exception e) {
			logger.error("初始化jdb-orm系统的配置信息时，出现异常");
			logger.error("初始化的xml配置内容为: {}", xmlDocument.asXML());
			logger.error("异常信息为:{}", ExceptionUtil.getExceptionDetailMessage(e));
			throw new ConfigurationInitialException("jdb-orm程序在初始化时出现异常", e);
		} finally {
			doDestroy();
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
	public SessionFactory getSessionFactory() {
		return buildSessionFactory();
	}
	
	@Override
	public void doDestroy() {
		logger.debug("{} 开始 destroy", getClass());
		if(properties != null) {
			properties.doDestroy();
		}
		if(environment != null) {
			environment.doDestroy();
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
	private void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	@Override
	public void selfChecking() throws SelfCheckingException {
	}
}
