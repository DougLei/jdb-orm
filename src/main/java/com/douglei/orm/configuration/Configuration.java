package com.douglei.orm.configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.environment.Environment;
import com.douglei.orm.configuration.environment.Properties;
import com.douglei.orm.mapping.MappingContainer;
import com.douglei.orm.sessionfactory.SessionFactory;
import com.douglei.tools.ExceptionUtil;

/**
 * 
 * @author DougLei
 */
public class Configuration {
	public static final String DEFAULT_CONFIGURATION_CLASSPATH_FILE_PATH = "jdb-orm.conf.xml"; // 默认的配置文件路径(基于java resource)
	private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
	
	private String id;// 配置的id; 优先级高于配置文件中的id
	private ExternalDataSource externalDataSource; // 外部的数据源; 优先级高于配置文件中的数据源
	private MappingContainer mappingContainer; // 映射容器
	
	
	/**
	 * 设置配置的id; 优先级高于配置文件中的id
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 设置外部的数据源; 优先级高于配置文件中的数据源
	 * @param exDataSource
	 */
	public void setExternalDataSource(ExternalDataSource exDataSource) {
		this.externalDataSource = exDataSource;
	}
	
	/**
	 * 设置映射容器; 不配置时框架会使用默认容器
	 * @param mappingContainer
	 */
	public void setMappingContainer(MappingContainer mappingContainer) {
		this.mappingContainer = mappingContainer;
	}
	
	
	/**
	 * 在默认路径(基于java resource)下寻找配置文件, 构建SessionFactory实例
	 * @return
	 */
	public SessionFactory buildSessionFactory() {
		logger.debug("在默认路径(基于java resource)下寻找配置文件, 构建SessionFactory实例: [{}]", DEFAULT_CONFIGURATION_CLASSPATH_FILE_PATH);
		return buildSessionFactory_(Configuration.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIGURATION_CLASSPATH_FILE_PATH));
	}
	
	/**
	 * 根据指定的文件(基于java resource), 构建SessionFactory实例
	 * @param resource
	 * @return
	 */
	public SessionFactory buildSessionFactory(String resource) {
		logger.debug("根据指定的文件(基于java resource), 构建SessionFactory实例: [{}]", resource);
		return buildSessionFactory_(Configuration.class.getClassLoader().getResourceAsStream(resource));
	}
	
	/**
	 * 根据指定的配置内容, 构建SessionFactory实例
	 * @param content
	 * @return
	 */
	public SessionFactory buildSessionFactoryByContent(String content) {
		logger.debug("根据指定的配置内容, 构建SessionFactory实例: \n{}", content);
		return buildSessionFactory_(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
	}
	
	/**
	 * 通过读取流中的数据, 构建SessionFactory实例
	 * @param input
	 * @return
	 */
	public SessionFactory buildSessionFactory(InputStream input) {
		logger.debug("通过读取流中的数据, 构建SessionFactory实例: [{}]", input);
		return buildSessionFactory_(input);
	}
	
	// 构建SessionFactory实例
	private SessionFactory buildSessionFactory_(InputStream input) {
		Environment environment = null;
		try {
			logger.debug("开始读取jdb-orm框架的配置信息, 构建[com.douglei.orm.sessionfactory.SessionFactory]实例");
			Element configurationElement = new SAXReader().read(input).getRootElement();
			
			// 设置配置id
			if(id == null) {
				id = configurationElement.attributeValue("id");
				if(id == null)
					id = UUID.randomUUID().toString();
			}
			
			// 解析<environment>
			Element environmentElement = Dom4jUtil.getElement("environment", configurationElement);
			Properties properties = new Properties(environmentElement.element("properties"));
			environment = new Environment(environmentElement, properties, externalDataSource, mappingContainer);
			
			// 构建SessionFactory实例
			logger.debug("结束读取jdb-orm框架的配置信息, 完成构建[com.douglei.orm.sessionfactory.SessionFactory]实例");
			return new SessionFactory(id, environment);
		} catch (Exception e) {
			try {
				logger.error("结束读取jdb-orm框架的配置信息, 构建[com.douglei.orm.sessionfactory.SessionFactory]实例时出现异常, 框架进行回退操作: \n{}", ExceptionUtil.getStackTrace(e));
				if(environment != null)
					environment.destroy();
			} catch (Exception e1) {
				logger.error("结束读取jdb-orm框架的配置信息, 构建[com.douglei.orm.sessionfactory.SessionFactory]实例出现异常后, 框架进行回退操作时又出现异常: \n{}", ExceptionUtil.getStackTrace(e1));
				e.addSuppressed(e1);
			}
			throw new OrmException("构建[com.douglei.orm.sessionfactory.SessionFactory]实例时出现异常", e);
		} finally {
			try {
				logger.debug("finally: 开始关闭读取jdb-orm框架的配置信息的流对象");
				input.close();
				logger.debug("finally: 结束关闭读取jdb-orm框架的配置信息的流对象");
			} catch (IOException e) {
				logger.error("关闭读取jdb-orm框架的配置信息的流对象时出现异常: \n{}", ExceptionUtil.getStackTrace(e));
				throw new OrmException("关闭读取jdb-orm框架的配置信息的流对象时出现异常", e);
			}
		}
	}
}
