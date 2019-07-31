package com.douglei.orm.configuration.impl.xml;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.configuration.ConfigurationInitializeException;
import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.SelfCheckingException;
import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.ext.configuration.ExtConfiguration;
import com.douglei.orm.configuration.impl.xml.element.environment.XmlEnvironment;
import com.douglei.orm.configuration.impl.xml.element.extconfiguration.XmlExtConfiguration;
import com.douglei.orm.configuration.impl.xml.element.properties.Properties;
import com.douglei.orm.configuration.impl.xml.util.Dom4jElementUtil;
import com.douglei.orm.context.XmlReaderContext;
import com.douglei.orm.sessionfactory.SessionFactory;
import com.douglei.orm.sessionfactory.impl.SessionFactoryImpl;
import com.douglei.tools.utils.CloseUtil;
import com.douglei.tools.utils.ExceptionUtil;
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
	 * 对应<extConfiguration>节点
	 */
	private ExtConfiguration extConfiguration;
	/**
	 * 对应<environment>节点
	 */
	private Environment environment;
	
	
	public XmlConfiguration() throws ConfigurationInitializeException, DestroyException{
		this(DEFAULT_CONF_FILE);
	}
	public XmlConfiguration(String configurationFile) throws ConfigurationInitializeException, DestroyException{
		this(XmlConfiguration.class.getClassLoader().getResourceAsStream(configurationFile));
	}
	public XmlConfiguration(InputStream in) throws ConfigurationInitializeException, DestroyException{
		if(logger.isDebugEnabled()) {
			logger.debug("开始初始化jdb-orm框架的配置信息, 完成{}实例的创建", Configuration.class.getName());
		}
		
		try {
			xmlDocument = XmlReaderContext.getConfigurationSAXReader().read(in);
			if(logger.isDebugEnabled()) {
				logger.debug("初始化的xml配置内容为: {}", xmlDocument.asXML());
			}
			Element root = xmlDocument.getRootElement();
			setId(root.attributeValue("id"));
			setProperties(new Properties(root.element("properties")));
			setExtConfiguration(new XmlExtConfiguration(root.element("extConfiguration")));
			setEnvironment(new XmlEnvironment(id, Dom4jElementUtil.validateElementExists("environment", root), properties, extConfiguration));
		} catch (Exception e) {
			logger.error("jdb-orm框架初始化时出现异常, 开始进行销毁: {}", ExceptionUtil.getExceptionDetailMessage(e));
			try {
				destroy();
			} catch (DestroyException e1) {
				throw e1;
			}
			throw new ConfigurationInitializeException("jdb-orm框架初始化时出现异常", e);
		} finally {
			CloseUtil.closeIO(in);
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("结束初始化jdb-orm框架的配置信息, 完成{}实例的创建", Configuration.class.getName());
		}
	}
	
	@Override
	public SessionFactory buildSessionFactory() {
		if(sessionFactory == null) {
			sessionFactory = new SessionFactoryImpl(this, environment);
		}
		return sessionFactory;
	}
	
	@Override
	public void destroy() throws DestroyException{
		if(logger.isDebugEnabled()) {
			logger.debug("{} 开始 destroy", getClass().getName());
		}
		
		try {
			if(properties != null) {
				properties.destroy();
			}
			if(extConfiguration != null) {
				extConfiguration.destroy();
			}
			if(environment != null) {
				environment.destroy();
			}
		} catch (Exception e) {
			logger.error("jdb-orm框架在销毁时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw new DestroyException("jdb-orm框架在销毁时出现异常", e);
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("{} 结束 destroy", getClass().getName());
		}
	}
	
	// -------------------------------------------------------------
	@Override
	public String getId() {
		return id;
	}
	
	private void setId(String id) {
		if(StringUtil.isEmpty(id)) {
			throw new NullPointerException("<configuration id=\"\">元素的id属性值不能为空");
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
