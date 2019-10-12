package com.douglei.orm.configuration;

import com.douglei.tools.instances.file.resources.reader.PropertiesConfigurationBeanReader;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class JdbConfigurationBean {
	private JdbConfigurationBean() {}
	private static final JdbConfigurationBean bean = new JdbConfigurationBean();
	static {
		new PropertiesConfigurationBeanReader("jdb.config.properties", bean);
	}
	public static final JdbConfigurationBean instance() {
		return bean;
	}
	
	
	private String sqlParameterPrefix= "#{";// 声明sql参数的前缀
	private String sqlParameterSuffix="}";// 声明sql参数的后缀

	
	public String getSqlParameterPrefix() {
		if(sqlParameterPrefix == null) {
			return "#{";
		}
		return sqlParameterPrefix;
	}
	public String getSqlParameterSuffix() {
		if(sqlParameterSuffix == null) {
			return "}";
		}
		return sqlParameterSuffix;
	}
	public void setSqlParameterPrefix(String sqlParameterPrefix) {
		if(StringUtil.notEmpty(sqlParameterPrefix)) {
			this.sqlParameterPrefix = sqlParameterPrefix.trim();
		}
	}
	public void setSqlParameterSuffix(String sqlParameterSuffix) {
		if(StringUtil.notEmpty(sqlParameterSuffix)) {
			this.sqlParameterSuffix = sqlParameterSuffix.trim();
		}
	}
}