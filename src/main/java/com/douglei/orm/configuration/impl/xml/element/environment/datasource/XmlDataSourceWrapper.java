package com.douglei.orm.configuration.impl.xml.element.environment.datasource;

import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.configuration.DestroyException;
import com.douglei.orm.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.orm.core.dialect.TransactionIsolationLevel;
import com.douglei.orm.core.sql.ConnectionWrapper;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class XmlDataSourceWrapper implements DataSourceWrapper{
	private static final Logger logger = LoggerFactory.getLogger(XmlDataSourceWrapper.class);
	
	private Map<String, String> propertyMap;
	
	private DataSource dataSource;
	private String closeMethodName;
	
	public XmlDataSourceWrapper() {
	}
	public XmlDataSourceWrapper(DataSource dataSource, String closeMethodName, Map<String, String> propertyMap) {
		this.dataSource = dataSource;
		this.closeMethodName = closeMethodName;
		this.propertyMap = propertyMap;
		invokeSetMethod();
	}
	
	/**
	 * 
	 */
	private void invokeSetMethod() {
		logger.debug("开始给数据源 {} 的属性设置值", dataSource.getClass().getName());
		IntrospectorUtil.setProperyValues(dataSource, propertyMap);
		logger.debug("结束给数据源 {} 的属性设置值", dataSource.getClass());
	}
	
	@Override
	public void destroy() throws DestroyException {
		if(logger.isDebugEnabled()) logger.debug("{} 开始 destroy", getClass().getName());
		if(dataSource != null && StringUtil.notEmpty(closeMethodName)) {
			try {
				logger.debug("{} {}", closeMethodName, dataSource.getClass().getName());
				dataSource.getClass().getMethod(closeMethodName).invoke(dataSource);
			} catch (Exception e) {
				throw new DestroyException(closeMethodName+" "+dataSource.getClass().getName()+" 时出现异常", e);
			}
		}
		if(logger.isDebugEnabled()) logger.debug("{} 结束 destroy", getClass().getName());
	}	
	
	@Override
	public DataSource getDataSource() {
		return dataSource;
	}
	
	@Override
	public ConnectionWrapper getConnection(boolean beginTransaction) {
		return getConnection(beginTransaction, null);
	}
	
	@Override
	public ConnectionWrapper getConnection(boolean beginTransaction, TransactionIsolationLevel transactionIsolationLevel) {
		return new ConnectionWrapper(dataSource, beginTransaction, transactionIsolationLevel);
	}
}
