package com.douglei.configuration.impl.xml.element.environment.datasource;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.DestroyException;
import com.douglei.configuration.SelfCheckingException;
import com.douglei.configuration.environment.datasource.DataSourceWrapper;
import com.douglei.configuration.impl.xml.element.environment.XmlEnvironment;
import com.douglei.database.sql.ConnectionWrapper;
import com.douglei.utils.StringUtil;
import com.douglei.utils.datatype.ConvertUtil;

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
	public XmlDataSourceWrapper(DataSource dataSource, String closeMethodName, Map<String, String> propertyMap, XmlEnvironment environment) {
		this.dataSource = dataSource;
		this.closeMethodName = closeMethodName;
		this.propertyMap = propertyMap;
		invokeSetMethod();
	}
	
	/**
	 * 
	 */
	private void invokeSetMethod() {
		logger.debug("开始给数据源 {} 的属性设置值", dataSource.getClass());
		if(propertyMap == null || propertyMap.size() == 0) {
			throw new NullPointerException("<datasource>元素下，必须配置必要的数据库连接参数");
		}
		String propertyName = null;
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(dataSource.getClass());
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			Object value = null;
			Method setter = null;
			for (PropertyDescriptor pd : pds) {
				propertyName = pd.getName();
				value = propertyMap.remove(propertyName);
				if(value != null) {
					setter = pd.getWriteMethod();
					if(setter == null) {
						throw new NullPointerException("can't invoke "+dataSource.getClass()+"."+fieldNameToSetMethodName(propertyName)+" method, 因为系统没有获取到该方法");
					}
					setter.invoke(dataSource, ConvertUtil.simpleConvert(value, pd.getPropertyType()));
					
					if(logger.isDebugEnabled()) {
						logger.debug("invoke {}.{} method, input parameter value is {}, value type is {}", dataSource.getClass(), setter.getName(), value, pd.getPropertyType());
					}
					if(propertyMap.size() == 0) {
						break;
					}
				}
			}
		} catch(IntrospectionException e) {
			throw new RuntimeException(e);
		} catch (NullPointerException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException("通过反射给数据源 ["+dataSource.getClass()+"] 的属性 ["+propertyName+"] set值时出现异常", e);
		}
		logger.debug("结束给数据源 {} 的属性设置值", dataSource.getClass());
	}
	private String fieldNameToSetMethodName(String fieldName) {
		return "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
	}
	
	@Override
	public void doDestroy() throws DestroyException {
		logger.debug("{} 开始 destroy", getClass());
		if(dataSource != null && StringUtil.notEmpty(closeMethodName)) {
			logger.debug("{} {}", closeMethodName, dataSource.getClass());
			try {
				dataSource.getClass().getMethod(closeMethodName).invoke(dataSource);
			} catch (Exception e) {
				throw new DestroyException(closeMethodName+" "+dataSource.getClass()+" 时出现异常", e);
			}
		}
		logger.debug("{} 结束 destroy", getClass());
	}	
	
	@Override
	public DataSource getDataSource() {
		return dataSource;
	}
	
	@Override
	public ConnectionWrapper getConnection(boolean beginTransaction) {
		return new ConnectionWrapper(dataSource, beginTransaction);
	}
	
	@Override
	public void selfChecking() throws SelfCheckingException {
	}
}
