package com.douglei.orm.configuration.impl.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.Configuration;
import com.douglei.orm.configuration.ConfigurationInitializeException;
import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.impl.xml.element.environment.XmlEnvironment;
import com.douglei.orm.configuration.impl.xml.element.properties.Properties;
import com.douglei.orm.configuration.impl.xml.util.Dom4jElementUtil;
import com.douglei.orm.context.xml.MappingXmlReaderContext;
import com.douglei.orm.sessionfactory.SessionFactoryImpl;
import com.douglei.tools.utils.ExceptionUtil;

/**
 * xml配置接口实现
 * @author DougLei
 */
public class XmlConfiguration extends Configuration {
	private static final Logger logger = LoggerFactory.getLogger(XmlConfiguration.class);
	
	/**
	 * 对应<properties>节点
	 */
	private Properties properties;
	/**
	 * 对应<environment>节点
	 */
	private Environment environment;
	
	public XmlConfiguration() throws ConfigurationInitializeException, DestroyException{
		setConfigurationFile(DEFAULT_CONF_FILE);
	}
	public XmlConfiguration(String configurationFile) throws ConfigurationInitializeException, DestroyException{
		setConfigurationFile(configurationFile);
	}
	
	@Override
	protected void setSessionFactory() {
		if(logger.isDebugEnabled()) {
			logger.debug("开始初始化jdb-orm框架的配置信息, 完成{}实例的创建", Configuration.class.getName());
		}
		try {
			Document xmlDocument = MappingXmlReaderContext.getConfigurationSAXReader().read(XmlConfiguration.class.getClassLoader().getResourceAsStream(configurationFile));
			if(logger.isDebugEnabled()) {
				logger.debug("初始化的xml配置内容为: {}", xmlDocument.asXML());
			}
			Element root = xmlDocument.getRootElement();
			setId(root.attributeValue("id"));
			this.properties = new Properties(root.element("properties"));
			this.environment = new XmlEnvironment(id, Dom4jElementUtil.validateElementExists("environment", root), properties, exDataSource, mappingStore);
			super.sessionFactory = new SessionFactoryImpl(this, environment);
		} catch (Exception e) {
			logger.error("jdb-orm框架初始化时出现异常, 开始进行销毁: {}", ExceptionUtil.getExceptionDetailMessage(e));
			try {
				destroy_();
			} catch (Exception e1) {
				logger.error("jdb-orm框架初始化出现异常后, 进行销毁时又出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e1));
				e.addSuppressed(e1);
			}
			throw new ConfigurationInitializeException("jdb-orm框架初始化时出现异常", e);
		} finally {
			MappingXmlReaderContext.destroy();
		}
		if(logger.isDebugEnabled()) {
			logger.debug("结束初始化jdb-orm框架的配置信息, 完成{}实例的创建", Configuration.class.getName());
		}
	}
	
	// 销毁
	private void destroy_(){
		if(logger.isDebugEnabled()) logger.debug("{} 开始 destroy", getClass().getName());
		if(properties != null) {
			properties.destroy();
		}
		if(environment != null) {
			environment.destroy();
		}
		if(logger.isDebugEnabled()) logger.debug("{} 结束 destroy", getClass().getName());
	}
	
	@Override
	public void destroy() throws DestroyException{
		try {
			destroy_();
		} catch (Exception e) {
			logger.error("jdb-orm框架在销毁时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw new DestroyException("jdb-orm框架在销毁时出现异常", e);
		}
	}
}
