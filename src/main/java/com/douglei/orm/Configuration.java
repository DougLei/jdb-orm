package com.douglei.orm;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.environment.Environment;
import com.douglei.orm.environment.properties.Properties;
import com.douglei.orm.mapping.container.MappingContainer;
import com.douglei.orm.mapping.execute.serialization.FolderContainer;
import com.douglei.orm.sessionfactory.SessionFactory;
import com.douglei.orm.util.Dom4jUtil;
import com.douglei.tools.utils.ExceptionUtil;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class Configuration {
	public static final String DEFAULT_CONFIGURATION_FILE_PATH = "jdb-orm.conf.xml"; // 默认的配置文件路径
	private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
	private InputStream inputstream; // 配置文件的流对象
	
	private String id;
	private Environment environment;
	
	private ExternalDataSource externalDataSource;
	private MappingContainer mappingContainer;
	
	private SessionFactory sessionFactory;
	
	public Configuration() {
		inputstream = Configuration.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIGURATION_FILE_PATH);
	}
	public Configuration(String configurationFilePath) {
		inputstream = Configuration.class.getClassLoader().getResourceAsStream(configurationFilePath);
	}
	public Configuration(InputStream configurationInputStream) {
		inputstream = configurationInputStream;
	}
	
	/**
	 * 获取SessionFactory实例
	 * @return
	 */
	public SessionFactory getSessionFactory() {
		if(sessionFactory == null) {
			if(logger.isDebugEnabled()) 
				logger.debug("开始初始化jdb-orm框架的配置信息, 构建[{}]实例", SessionFactory.class);
			
			Properties properties = null; // 对应<properties>元素, 在框架加载完成后即可销毁
			try {
				Document xmlDocument = new SAXReader().read(inputstream);
				Element root = xmlDocument.getRootElement();
				setId(root.attributeValue("id"));
				properties = new Properties(root.element("properties"));
				this.environment = new Environment(id, Dom4jUtil.getElement("environment", root), properties, externalDataSource, mappingContainer);
				this.sessionFactory = new SessionFactory(this, environment);
			} catch (Exception e) {
				logger.error("初始化jdb-orm框架的配置信息, 构建[{}]实例时, 出现异常: {}", SessionFactory.class, ExceptionUtil.getExceptionDetailMessage(e));
				try {
					destroy_();
				} catch (Exception e1) {
					logger.error("初始化jdb-orm框架的配置信息, 构建[{}]实例出现异常后, 在进行自动销毁时, 又出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e1));
					e.addSuppressed(e1);
				}
				throw new ConfigurationInitializeException("jdb-orm框架初始化时出现异常", e);
			} finally {
				if(properties != null)
					properties.clear();
				if(logger.isDebugEnabled()) 
					logger.debug("结束初始化jdb-orm框架的配置信息, 构建[{}]实例", SessionFactory.class);
			}
		}
		return sessionFactory;	
	}
	
	/**
	 * 销毁
	 */
	public final void destroy() {
		try {
			destroy_();
		} catch (Exception e) {
			logger.error("jdb-orm框架在销毁时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
			throw new DestroyException("jdb-orm框架在销毁时出现异常", e);
		}
	}
	private void destroy_() throws Exception { 
		if(environment != null)
			environment.destroy();
		FolderContainer.deleteFolder(this.id);
	}
	
	/**
	 * 设置id
	 * @param id
	 */
	public void setId(String id) {
		if(this.id == null) {
			if(StringUtil.isEmpty(id)) 
				throw new NullPointerException("["+getClass().getName() + "]的id属性值不能为空");
			this.id = id;
			FolderContainer.createFolder(this.id);
		}
	}
	
	/**
	 * 设置外部的数据源
	 * @param exDataSource
	 */
	public void setExternalDataSource(ExternalDataSource exDataSource) {
		this.externalDataSource = exDataSource;
	}
	
	/**
	 * 设置映射的存储容器
	 * @param mappingContainer
	 */
	public void setMappingContainer(MappingContainer mappingContainer) {
		this.mappingContainer = mappingContainer;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}
}
